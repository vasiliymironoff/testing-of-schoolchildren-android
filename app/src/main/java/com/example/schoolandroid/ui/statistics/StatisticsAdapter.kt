package com.example.schoolandroid.ui.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.Statistics
import com.example.schoolandroid.databinding.LayoutStatisticsBinding
import com.example.schoolandroid.util.Util
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.roundToInt

class StatisticsAdapter(
    var statistics: List<Statistics>,
    var activity: MainActivity
) : RecyclerView.Adapter<StatisticsAdapter.StatisticHolder>() {

    class StatisticHolder(val view: View, val activity: MainActivity) :
        RecyclerView.ViewHolder(view) {
        val binding = LayoutStatisticsBinding.bind(view)

        fun bind(stat: Statistics) {
            binding.title.text = stat.exam.title
            binding.total.text = "Максимальный балл: ${stat.total}"
            binding.grade.text = "Набранный балл: ${stat.grade}"
            val percent = ((stat.grade / stat.total.toDouble()) * 100).roundToInt()
            binding.percent.text = " ${percent.toString()}%"
            val startTime = Util.utilTimeToFormatForUI(stat.startTime)
            binding.startTime.text =
                "Начало выполнения: ${startTime}"
            binding.timePassing.text =
                "Время выполнения: ${((stat.endTime - stat.startTime) / 60).roundToInt()} минуты"
            binding.color.setCircleBackgroundColorResource(Util.percentToColor(percent))
            if (stat.endTime.toInt() == stat.startTime.toInt()) {
                binding.notComplete.visibility = View.VISIBLE
            } else {
                binding.notComplete.visibility = View.GONE
            }

            if (stat.errorsStatistics.isNotEmpty()) {
                binding.listErrorButton.visibility = View.VISIBLE
                val stringBuilder = StringBuilder()
                for (elem in stat.errorsStatistics) {
                    stringBuilder.append("${elem.question}\n")
                }
                val errors: String = stringBuilder.toString()
                binding.listErrorButton.setOnClickListener {
                    val alertDialog: AlertDialog? = activity?.let {
                        val builder = MaterialAlertDialogBuilder(it)
                        builder.apply {
                            setTitle(activity.getString(R.string.title_dialog_errors))
                            setPositiveButton("OK", { dialog, id ->
                                dialog.cancel()
                            })
                            setMessage(errors)
                        }
                        builder.create()
                    }
                    alertDialog?.show()
                }
            } else {
                binding.listErrorButton.visibility = View.GONE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_statistics, parent, false)
        return StatisticHolder(view, activity)
    }

    override fun onBindViewHolder(holder: StatisticHolder, position: Int) {
        holder.bind(statistics.get(position))
    }

    override fun getItemCount(): Int {
        return statistics.size
    }

}