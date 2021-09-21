package com.example.schoolandroid.ui.exampassing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentResultBinding
import com.example.schoolandroid.util.Util
import kotlin.math.roundToInt


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        _binding = FragmentResultBinding.bind(view)

        (activity as MainActivity).hideUIForPerformanceExam()

        val grade = requireArguments().getInt(GRADE)
        val total = requireArguments().getInt(TOTAL)
        val errors = requireArguments().getString(MESSAGE)

        val resultGrade = "${grade}/${total}"
        val percent = (grade / total.toDouble() * 100).roundToInt()

        binding.result.text = resultGrade
        binding.errors.text = errors
        binding.percent.text = "${percent}%"
        binding.color.setCircleBackgroundColorResource(Util.percentToColor(percent))

        binding.toMain.setOnClickListener {
            exit()
        }
        return view
    }

    private fun exit() {
        (activity as MainActivity).showUIForPerformanceExam()
        findNavController().navigate(R.id.action_resultFragment_to_navigation_study)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).onBackPressedDispatcher.addCallback {
            exit()
        }
    }

    companion object {
        const val TOTAL = "TOTAL"
        const val GRADE = "GRADE"
        const val MESSAGE = "MESSAGE"
    }
}