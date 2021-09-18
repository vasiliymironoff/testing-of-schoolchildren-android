package com.example.schoolandroid.ui.study

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.ExamForList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudyViewModel : ViewModel() {
    private val exams = MutableLiveData<List<ExamForList>>()

    public fun fetchData() {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService().getListExams()
                }
                exams.value = response
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    public fun getExams(): LiveData<List<ExamForList>> {
        return exams
    }


}