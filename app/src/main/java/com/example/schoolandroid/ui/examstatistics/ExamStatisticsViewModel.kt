package com.example.schoolandroid.ui.examstatistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.ExamStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamStatisticsViewModel : ViewModel() {
    private val statistics = MutableLiveData<ExamStatistics>()

    fun getStatistics(): LiveData<ExamStatistics> {
        return statistics
    }

    fun fetchStatistics(idExam: Int) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.getExamStatistics(idExam)
                }
                if (response != null) {
                    statistics.value = response!!
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
}