package com.example.schoolandroid.ui.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.ExamForList
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.example.schoolandroid.ui.examdetail.ExamDetailFragment
import com.example.schoolandroid.ui.study.ExamAdapter
import com.example.schoolandroid.ui.study.MovableToVerboseExam
import com.squareup.picasso.Picasso
import java.lang.Exception


class ProfileFragment : Fragment(), MovableToVerboseExam {

    companion object {
        const val PICK = 123
    }
    private val viewModel: ProfileViewModel by activityViewModels()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: ExamAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        viewModel.getCurrentUser().observe(viewLifecycleOwner, {
            binding.swiper.isRefreshing = false
            try {
                if (it != null) {
                    binding.name.text = "${it.firstName} ${it.lastName}"

                    binding.email.text = "Email: ${it.email}"

                    if (it.avatar != null) {
                        binding.addPhotoImage.visibility = View.GONE
                        Picasso.get()
                            .load(it.avatar)
                            .into(binding.avatar)
                    }

                    binding.avatar.setOnClickListener {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        activity?.startActivityForResult(Intent.createChooser(intent,
                            "Выберите приложения для загрузки фото"), PICK)
                    }

                } else {
                    viewModel.fetchMe()
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }

        })
        binding.swiper.setOnRefreshListener {
            viewModel.fetchMe()
        }
        binding.swiper.isRefreshing = true
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_profileEditFragment)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("key")
            ?.observe(viewLifecycleOwner){
            if (it) {
                viewModel.fetchMe()
            }
        }
        adapter = ExamAdapter(ArrayList<ExamForList>(), this)
        binding.recyclerExamsMe.adapter = adapter
        binding.recyclerExamsMe.layoutManager = LinearLayoutManager(context)
        viewModel.fetchMyExams()
        viewModel.getMyExams().observe(viewLifecycleOwner) {
            adapter?.exams = it
            adapter?.notifyDataSetChanged()
        }
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun moveToVerboseExam(id: Int) {
        findNavController().navigate(
            R.id.action_navigation_profile_to_examDetailFragment,
            bundleOf(ExamDetailFragment.EXAM_ID to id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}