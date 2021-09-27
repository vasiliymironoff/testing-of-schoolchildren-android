package com.example.schoolandroid.ui.examstatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentExamStatisticsBinding
import com.example.schoolandroid.ui.examdetail.ExamDetailFragment.Companion.EXAM_ID

class ExamStatisticsFragment : Fragment() {

    private var _binding: FragmentExamStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExamStatisticsAdapter
    private val viewModel: ExamStatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exam_statistics, container, false)
        _binding = FragmentExamStatisticsBinding.bind(view)
        viewModel.fetchStatistics(requireArguments().getInt(EXAM_ID))
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_round_close_24)
        adapter = ExamStatisticsAdapter(ArrayList(), activity as MainActivity)
        binding.statisticsRecycler.adapter = adapter
        binding.statisticsRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getStatistics().observe(viewLifecycleOwner) {
            if (it.statistics.isEmpty()) {
                binding.notResult.visibility = View.VISIBLE
            } else {
                binding.notResult.visibility = View.GONE
                adapter.statistics = it.statistics
                adapter.notifyDataSetChanged()
            }

        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}