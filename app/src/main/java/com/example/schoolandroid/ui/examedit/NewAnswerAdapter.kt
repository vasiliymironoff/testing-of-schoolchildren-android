package com.example.schoolandroid.ui.examedit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.LayoutAddAnswerBinding
import com.example.schoolandroid.ui.examedit.model.ObservableAnswer
import okhttp3.internal.notify

class NewAnswerAdapter(var listAnswer: ArrayList<ObservableAnswer>, val viewModel: ExamEditViewModel) : RecyclerView.Adapter<NewAnswerAdapter.AnswerHolder>() {

    class AnswerHolder(val view: View, val viewModel: ExamEditViewModel) : RecyclerView.ViewHolder(view) {
        val binding = LayoutAddAnswerBinding.bind(view)

        fun bind(listAnswer: ArrayList<ObservableAnswer>, position: Int, adapter: NewAnswerAdapter) {
            binding.answer = listAnswer[position]
            binding.deleteAnswer.setOnClickListener {
                if (listAnswer[position].id != null) {
                    viewModel.deleteAnswer(listAnswer[position].id!!)
                }
                listAnswer.remove(listAnswer[position])
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_answer, parent, false)
        return AnswerHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.bind(listAnswer, position, this)
    }

    override fun getItemCount(): Int {
        return listAnswer.size
    }
}