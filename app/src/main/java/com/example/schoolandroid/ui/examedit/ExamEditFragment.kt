package com.example.schoolandroid.ui.examedit

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentExamEditBinding
import com.example.schoolandroid.ui.examedit.model.ObservableTask
import com.google.android.material.snackbar.Snackbar


class ExamEditFragment : Fragment() {

    private var _binding: FragmentExamEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExamEditViewModel by viewModels()

    private lateinit var tasksAdapter: NewTaskAdapter

    companion object {
        const val CREATE_EXAM = "CREATE_EXAM"
        const val EDIT_EXAM = "EDIT_EXAM"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exam_edit, container, false)
        _binding = FragmentExamEditBinding.bind(view)
        binding.viewModel = viewModel
        (activity as MainActivity).hideBottomNavigationView()
        setAutoCompleteTextView(view)
        setTitle()

        if (arguments?.getInt(EDIT_EXAM) != null) {
            binding.publishExam.text = resources.getString(R.string.publish_edit_exam)
            viewModel.getExam(requireArguments().getInt(EDIT_EXAM, -1))
        } else {
            viewModel.initListTask()
        }

        binding.addTaskButton.setOnClickListener {
            viewModel.addTask()
        }
        initTasksRecycler()
        binding.publishExam.setOnClickListener {
            if (arguments?.getInt(EDIT_EXAM) != null) {
                if(viewModel.putExam(it)) {
                    findNavController().popBackStack()
                }
            } else {
                if(viewModel.postExam(it)) {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("key", true)
                    findNavController().popBackStack()
                }
            }
        }
        return view
    }

    private fun initTasksRecycler() {
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view_tasks)
        tasksAdapter = NewTaskAdapter(ArrayList(), requireContext(), viewModel)
        binding.recyclerViewTasks.adapter = tasksAdapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getTasks().observe(viewLifecycleOwner, {
            tasksAdapter.tasks = it
            tasksAdapter.notifyDataSetChanged()
        })
    }
    private fun setAutoCompleteTextView(view: View) {
        val spinnerClassroom: AutoCompleteTextView = view.findViewById(R.id.classroom)
        val classroomAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.classroom_choices)
        )
        classroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClassroom.setAdapter(classroomAdapter)

        val autoCompleteSubject: AutoCompleteTextView = view.findViewById(R.id.subject)
        val subjectAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.subject_choices)
        )
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteSubject.setAdapter(subjectAdapter)
    }

    private fun setTitle() {
        setHasOptionsMenu(true)
        if (arguments?.getInt(EDIT_EXAM) != null) {
            (activity as MainActivity)?.supportActionBar?.title = resources.getString(R.string.edit_exam)
        } else {
            (activity as MainActivity)?.supportActionBar?.title = resources.getString(R.string.add_exam)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (arguments?.getInt(EDIT_EXAM) != null) {
            inflater.inflate(R.menu.delete_exam, menu)
        } else {
            inflater.inflate(R.menu.edit_exam_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            viewModel.reset()
        }
        if (item.itemId == R.id.delete_exam) {
            val alertDialog = activity?.let{
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.warnion))
                    .setMessage(resources.getString(R.string.message_delete_exam_dialog))
                    .setNegativeButton("Отмена") { dialog, id ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Удалить проверочную") { dialog, id ->
                        if (viewModel.deleteExam(arguments?.getInt(EDIT_EXAM)!!)) {
                            findNavController().popBackStack()
                        }
                    }
                builder.create()
            }
            alertDialog?.show()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNavigationView()
        _binding = null
    }
}