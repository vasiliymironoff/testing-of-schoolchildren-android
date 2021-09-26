package com.example.schoolandroid.ui.examedit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.App
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.LayoutAddTaskBinding
import com.example.schoolandroid.ui.examedit.model.ObservableAnswer
import com.example.schoolandroid.ui.examedit.model.ObservableTask

class NewTaskAdapter(var tasks: ArrayList<ObservableTask>, val context: Context, val viewModel: ExamEditViewModel) : RecyclerView.Adapter<NewTaskAdapter.TaskHolder>(){

    class TaskHolder(val view: View, val viewModel: ExamEditViewModel) : RecyclerView.ViewHolder(view) {
        val binding = LayoutAddTaskBinding.bind(view)

        fun bind(tasks: ArrayList<ObservableTask>, position: Int, context: Context, adapter: NewTaskAdapter) {
            binding.task = tasks[position]
            binding.presenter = Presenter()
            binding.deleteTask.setOnClickListener {
                if (tasks[position].id != null) {
                    viewModel.deleteTask(tasks[position].id!!)
                }
                tasks.remove(tasks[position])
                adapter.notifyDataSetChanged()
            }
            binding.questionLayout.hint = context.resources.getString(R.string.question) + " " + (position + 1).toString()
            val adapter = NewAnswerAdapter(tasks[position].answers, viewModel)
            binding.addAnswerButton.setOnClickListener {
                tasks[position].answers.add(ObservableAnswer())
                adapter.listAnswer = tasks[position].answers
                adapter.notifyDataSetChanged()
            }

            binding.recyclerViewAnswers.adapter = adapter
            binding.recyclerViewAnswers.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_task, parent, false)
        return TaskHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks, position, context, this)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}