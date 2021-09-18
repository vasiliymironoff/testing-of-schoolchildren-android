package com.example.schoolandroid.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private val statisticsViewModel: StatisticsViewModel by activityViewModels()
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatisticsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = StatisticsAdapter(listOf(), activity as MainActivity)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        if (savedInstanceState == null) {
            statisticsViewModel.fetchStatistics()
        }
        statisticsViewModel.getStatistics().observe(viewLifecycleOwner, {
            adapter.statistics = it
            adapter.notifyDataSetChanged()
            binding.swiper.isRefreshing = false
        })
        binding.swiper.setOnRefreshListener {
            statisticsViewModel.fetchStatistics()
        }
        binding.swiper.isRefreshing = true
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}