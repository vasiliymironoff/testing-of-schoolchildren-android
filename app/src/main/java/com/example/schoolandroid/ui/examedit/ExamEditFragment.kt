package com.example.schoolandroid.ui.examedit

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private val viewModel: ExamEditViewModel by activityViewModels()

    private lateinit var tasksAdapter: NewTaskAdapter

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
        binding.addTaskButton.setOnClickListener {
            viewModel.addTask()
        }
        initTasksRecycler()
        binding.publishExam.setOnClickListener {
            if(viewModel.postExam(it)) {
                findNavController().popBackStack()
            }
        }
        return view
    }

    private fun initTasksRecycler() {
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view_tasks)
        tasksAdapter = NewTaskAdapter(ArrayList(), requireContext())
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
        (activity as MainActivity)?.supportActionBar?.title = resources.getString(R.string.add_exam)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_exam_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNavigationView()
        _binding = null
    }
}