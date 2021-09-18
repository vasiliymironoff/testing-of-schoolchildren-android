package com.example.schoolandroid.ui.exampassing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.Answer
import com.example.schoolandroid.databinding.LayoutAnswerBinding


class AnswerAdapter(
    var answers: List<Answer>,
    val manyOption: Boolean,
    var checkableList: List<ObservableField<TextAndBoolean>>,
    val context: Context
) : RecyclerView.Adapter<AnswerAdapter.AnswerHolder>() {

    class AnswerHolder(val binding: LayoutAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            answer: Answer,
            manyOption: Boolean,
            observableListBoolean: List<ObservableField<TextAndBoolean>>,
            position: Int
        ) {
            binding.data = observableListBoolean[position]
            binding.manyOption = manyOption
            if (!manyOption) {
                binding.root.findViewById<RadioButton>(R.id.answer_radio).setOnClickListener {
                    val selectedText =
                        it.findViewById<RadioButton>(R.id.answer_radio).text.toString()
                    observableListBoolean.forEach {
                        if (selectedText != it.get()?.text) {
                            it.set(TextAndBoolean(it.get()?.text ?: "", false))
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            DataBindingUtil.inflate<LayoutAnswerBinding>(
                LayoutInflater.from(context),
                R.layout.layout_answer,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.bind(answers[position], manyOption, checkableList, position)
    }

    override fun getItemCount(): Int {
        return answers.size
    }

}