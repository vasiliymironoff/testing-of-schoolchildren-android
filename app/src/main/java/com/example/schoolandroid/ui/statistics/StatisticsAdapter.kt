package com.example.schoolandroid.ui.statistics

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.Statistics
import com.example.schoolandroid.databinding.LayoutStatisticsBinding
import com.example.schoolandroid.util.Util
import java.text.SimpleDateFormat
import kotlin.math.acos
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
            binding.startTime.text =
                "Начало выполнения: ${Util.utilTimeToFormatForUI(stat.startTime)}"
            binding.timePassing.text =
                "Время выполнения: ${((stat.endTime - stat.startTime) / 60).roundToInt()} минуты"
            val color = when (percent / 10 * 10) {
                10 -> R.color.percent_10
                20 -> R.color.percent_20
                30 -> R.color.percent_30
                40 -> R.color.percent_40
                50 -> R.color.percent_50
                60 -> R.color.percent_60
                70 -> R.color.percent_70
                80 -> R.color.percent_80
                90 -> R.color.percent_90
                100 -> R.color.percent_100
                else -> R.color.percent_10
            }
            binding.color.setCircleBackgroundColorResource(color)
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