package com.example.schoolandroid.ui.exampassing

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.Task
import com.example.schoolandroid.databinding.LayoutTaskBinding

class TaskAdapter(
    var tasks: List<Task>,
    val checkableList: List<List<ObservableField<TextAndBoolean>>>,
    val context: Context
) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutTaskBinding.bind(view)
        private lateinit var adapter: AnswerAdapter

        fun bind(task: Task, position: Int, checkableList: List<ObservableField<TextAndBoolean>>) {
            val bal = if (task.scores % 10 == 1 && task.scores / 10 != 1) {
                "балл"
            } else if (task.scores % 10 in listOf(2, 3, 4) && task.scores / 10 != 1) {
                "балла"
            } else {
                "баллов"
            }
            binding.question.text = "${position + 1}. ${task.question} (${task.scores} $bal)"
            adapter = AnswerAdapter(task.answers, task.manyOption, checkableList, context)
            binding.answers.adapter = adapter
            binding.answers.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false)
        return TaskHolder(view, context)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(tasks[position], position, checkableList[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}