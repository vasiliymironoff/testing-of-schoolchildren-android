package com.example.schoolandroid.ui.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Statistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class StatisticsViewModel : ViewModel() {

    private val statistics = MutableLiveData<List<Statistics>>()

    public fun getStatistics(): LiveData<List<Statistics>> {
        return statistics
    }

    public fun fetchStatistics() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    App.getService().getListStatistics()
                }
                statistics.value = response
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }

        }
    }
}