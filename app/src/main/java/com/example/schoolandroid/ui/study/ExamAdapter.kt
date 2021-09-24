package com.example.schoolandroid.ui.study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.ExamForList
import com.example.schoolandroid.databinding.LayoutExamBinding
import com.example.schoolandroid.util.Util
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ExamAdapter(var exams: List<ExamForList>, val fragment: StudyFragment) :
    RecyclerView.Adapter<ExamAdapter.ExamHolder>() {

    class ExamHolder(val view: View, val fragment: StudyFragment) : RecyclerView.ViewHolder(view) {
        val binding = LayoutExamBinding.bind(view)

        fun bind(exam: ExamForList) {
            binding.name.text = "${exam.author.firstName} ${exam.author.lastName}"
            binding.classroom.text = "${exam.classRoom} класс"
            binding.subject.text = "${Util.getSubjectFromAbbreviation(exam.subject)}"
            binding.title.text = "${exam.title}"

            binding.publishTime.text = "${Util.utilTimeToFormatForUI(exam.publishTime)}"
            Picasso.get()
                .load(exam.author.avatar)
                .into(binding.avatar)
            binding.verbose.setOnClickListener {
                fragment.moveToVerboseExam(exam.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_exam, parent, false)
        return ExamHolder(view, fragment)
    }

    override fun onBindViewHolder(holder: ExamHolder, position: Int) {
        holder.bind(exams.get(position))
    }

    override fun getItemCount(): Int {
        return exams.size
    }
}