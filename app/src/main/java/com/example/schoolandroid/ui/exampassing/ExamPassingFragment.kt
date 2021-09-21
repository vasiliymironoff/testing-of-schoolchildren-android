package com.example.schoolandroid.ui.exampassing

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentExamPassingBinding
import com.example.schoolandroid.ui.examdetail.ExamDetailFragment.Companion.EXAM_ID
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.StringBuilder
import kotlin.math.roundToInt

class ExamPassingFragment : Fragment() {

    private var _binding: FragmentExamPassingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExamPassingViewModel by viewModels()

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exam_passing, container, false)
        _binding = FragmentExamPassingBinding.bind(view)
        if (savedInstanceState == null) {
            viewModel.fetchExam(requireArguments().getInt(EXAM_ID))
            viewModel.startTime()
        }
        (activity as MainActivity).hideUIForPerformanceExam()
        viewModel.getExam().observe(viewLifecycleOwner, {
            binding.title.text = it.title
            taskAdapter.tasks = it.tasks
            taskAdapter.notifyDataSetChanged()
            viewModel.postStatistics(requireArguments().getInt(EXAM_ID), it.maxScores)

        })
        viewModel.time.observe(viewLifecycleOwner, {
            (activity as MainActivity).supportActionBar?.title = "${it / 60} мин ${it % 60} сек"
        })
        taskAdapter = TaskAdapter(listOf(), viewModel.answers, requireContext())
        binding.tasks.adapter = taskAdapter
        binding.tasks.layoutManager = LinearLayoutManager(context)
        binding.sendResult.setOnClickListener {
            val result = viewModel.checkWork()
            val messageBuilder = StringBuilder()
            result.errors.forEach {
                messageBuilder.append("${it.question}\n")
            }
            val message: String = messageBuilder.toString()

            findNavController().navigate(R.id.action_examPassingFragment_to_resultFragment,
                bundleOf(ResultFragment.GRADE to result.grade,
                                ResultFragment.TOTAL to result.total,
                                ResultFragment.MESSAGE to message))
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        val alertDialog: AlertDialog? = activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.apply {
                setTitle("Прервать выполнение")
                setMessage("Данное действие нельзя отменить. Все ответы будут удалены, а результатом будет 0 баллов.")
                setPositiveButton(
                    getString(R.string.positive_button_exam),
                    DialogInterface.OnClickListener { dialog, id ->
                        (activity as MainActivity).showUIForPerformanceExam()
                        findNavController().navigate(R.id.action_examPassingFragment_to_navigation_study)
                    })
                setNeutralButton(
                    getString(R.string.negative_button_exam),
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            }
            builder.create()
        }
        (activity as MainActivity).onBackPressedDispatcher.addCallback {
            alertDialog?.show()
        }
    }
}