package com.example.schoolandroid.ui.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentStudyBinding
import com.example.schoolandroid.ui.examdetail.ExamDetailFragment
import com.example.schoolandroid.ui.examedit.ExamEditFragment.Companion.CREATE_EXAM
import com.example.schoolandroid.ui.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

class StudyFragment : Fragment(), MovableToVerboseExam{

    private val studyViewModel: StudyViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private var _binding: FragmentStudyBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExamAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        profileViewModel.getCurrentUser().observe(viewLifecycleOwner, {
            if (it.isTeacher) {
                binding.fab.visibility = View.VISIBLE
            }
        })
        adapter = ExamAdapter(listOf(), this)
        binding.recycler.layoutManager = LinearLayoutManager(this.context)
        binding.recycler.adapter = adapter
        if (savedInstanceState == null) {
            studyViewModel.fetchData()
        }
        studyViewModel.getExams().observe(viewLifecycleOwner, {
            adapter.exams = it
            adapter.notifyDataSetChanged()
            binding.swiper.isRefreshing = false
        })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_study_to_examEditFragment)
        }
        binding.swiper.setOnRefreshListener {
            studyViewModel.fetchData()
        }
        binding.swiper.isRefreshing = true

        return root
    }

    override fun onStart() {
        super.onStart()
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("key")
            ?.observe(viewLifecycleOwner) {
                if (it) {
                    Snackbar.make(binding.root, "Проверочная опубликована", Snackbar.LENGTH_SHORT).show()
                    studyViewModel.fetchData()
                }

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun moveToVerboseExam(id: Int) {
        findNavController().navigate(
            R.id.action_navigation_study_to_examDetailFragment,
            bundleOf(ExamDetailFragment.EXAM_ID to id)
        )
    }
}