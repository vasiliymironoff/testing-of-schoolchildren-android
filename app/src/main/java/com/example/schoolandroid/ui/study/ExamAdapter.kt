package com.example.schoolandroid.ui.study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.ExamForList
import com.example.schoolandroid.databinding.LayoutExamBinding
import com.example.schoolandroid.ui.examedit.ExamEditFragment.Companion.EDIT_EXAM
import com.example.schoolandroid.ui.profile.ProfileFragment
import com.example.schoolandroid.ui.profileedit.ProfileEditFragment
import com.example.schoolandroid.util.Util
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ExamAdapter(var exams: List<ExamForList>, val fragment: MovableToVerboseExam) :
    RecyclerView.Adapter<ExamAdapter.ExamHolder>() {

    class ExamHolder(val view: View, val fragment: MovableToVerboseExam) : RecyclerView.ViewHolder(view) {
        val binding = LayoutExamBinding.bind(view)

        fun bind(exam: ExamForList) {
            binding.name.text = "${exam.author.firstName} ${exam.author.lastName}"
            binding.classroom.text = "Класс: ${exam.classRoom} класс"
            binding.subject.text = "Предмет: ${Util.getSubjectFromAbbreviation(exam.subject)}"
            binding.title.text = "${exam.title}"

            binding.publishTime.text = "${Util.utilTimeToFormatForUI(exam.publishTime)}"
            if (exam.author.avatar == null) {
                binding.avatar.setBackgroundResource(R.drawable.ic_round_person_24)
            } else {
                Picasso.get()
                    .load(exam.author.avatar)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .resize(50, 50)
                    .into(binding.avatar)
            }
            binding.verbose.setOnClickListener {
                fragment.moveToVerboseExam(exam.id)
            }
            if (fragment is ProfileFragment) {
                binding.editExam.visibility = View.VISIBLE
                binding.editExam.setOnClickListener {
                    fragment.findNavController().navigate(R.id.action_navigation_profile_to_examEditFragment,
                        bundleOf(EDIT_EXAM to exam.id))
                }
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