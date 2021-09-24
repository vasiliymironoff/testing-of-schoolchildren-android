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
import com.example.schoolandroid.ui.profile.ProfileViewModel

class StudyFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun moveToVerboseExam(id: Int) {
        findNavController().navigate(
            R.id.action_navigation_study_to_examDetailFragment,
            bundleOf(ExamDetailFragment.EXAM_ID to id)
        )
    }
}