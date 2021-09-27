package com.example.schoolandroid.ui.examdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.CommentForPostAndPut
import com.example.schoolandroid.databinding.FragmentExamDetailBinding
import com.example.schoolandroid.ui.profile.ProfileViewModel
import com.example.schoolandroid.util.Util
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso

class ExamDetailFragment : Fragment() {

    companion object {
        const val EXAM_ID = "EXAM_ID"
    }

    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewModel: ExamDetailViewModel by viewModels()
    private var _binding: FragmentExamDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null) {
            viewModel.fetchExam(requireArguments().getInt(EXAM_ID))
        }
        val view = inflater.inflate(R.layout.fragment_exam_detail, container, false)
        _binding = FragmentExamDetailBinding.bind(view)

        binding.recyclerComments.layoutManager = LinearLayoutManager(context)

        (activity as MainActivity).onBackPressedDispatcher.addCallback {
            if (viewModel.isEdit) {
                viewModel.isEdit = false
                binding.textComment.text = null
                binding.sendComment.text = getString(R.string.send_comment)
            } else {
                findNavController().popBackStack()
            }
        }
        initClickListener()
        initLiveData()
        return view
    }
    private fun initClickListener() {
        binding.sendComment.setOnClickListener {
            val comment = CommentForPostAndPut(
                arguments?.getInt(EXAM_ID, -1) ?: -1,
                binding.textComment.text.toString()
            )
            val author = profileViewModel.getUserForAuthor()
            viewModel.sendComment(comment, author)
            binding.textComment.text = null
            binding.sendComment.text = getString(R.string.send_comment)
        }

        binding.statisticsButton.setOnClickListener {
            findNavController().navigate(R.id.action_examDetailFragment_to_examStatisticsFragment,
                bundleOf(EXAM_ID to requireArguments().getInt(EXAM_ID)))
        }

    }
    private fun initLiveData() {
        viewModel.getEditComment().observe(viewLifecycleOwner, {
            binding.textComment.setText(it.text)
            binding.sendComment.text = "Обновить комментарий"
        })

        viewModel.getExam().observe(viewLifecycleOwner, {
            (activity as AppCompatActivity).supportActionBar?.title = it.title
            Picasso.get()
                .load(it.author.avatar)
                .into(binding.avatar)
            binding.name.text = "${it.author.firstName} ${it.author.lastName}"
            binding.title.text = it.title
            binding.classroom.text = "Класс: ${it.classRoom} класс"
            binding.description.text = "Описание: ${it.description}"
            binding.subject.text = "Предмет: ${Util.getSubjectFromAbbreviation(it.subject)}"
            binding.maxScopes.text = "Максимальный балл: ${it.maxScores}"
            binding.countTask.text = "Количество заданий: ${it.countTask}"
            if (it.comments.isEmpty()) {
                binding.commentsTitle.text = "Комментариев пока нет"
            } else {
                adapter.comments = it.comments
                adapter.notifyDataSetChanged()
            }
            binding.run.setOnClickListener {
                findNavController().navigate(
                    R.id.action_examDetailFragment_to_examPassingFragment,
                    bundleOf(EXAM_ID to requireArguments().getInt(EXAM_ID))
                )
            }
        })

        profileViewModel.getCurrentUser().observe(viewLifecycleOwner, {
            adapter = CommentAdapter(listOf(), it.id, viewModel)
            binding.recyclerComments.adapter = adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}