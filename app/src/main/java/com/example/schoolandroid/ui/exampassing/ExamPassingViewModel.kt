package com.example.schoolandroid.ui.exampassing

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.ExamWithTaskForRetrieve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamPassingViewModel : ViewModel() {

    private val exam = MutableLiveData<ExamWithTaskForRetrieve>()
    val time = MutableLiveData(0)
    val answers = ArrayList<List<ObservableField<TextAndBoolean>>>()

    fun fetchExam(id: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    App.getService().getExamDetail(id)
                }
                exam.value = response
                val answersBoolean = ArrayList<ArrayList<ObservableField<TextAndBoolean>>>()
                val lastIndexTasks = response.tasks.size - 1
                for (i in 0..lastIndexTasks) {
                    answersBoolean.add(ArrayList())
                    val lastIndexAnswers = response.tasks[i].answers.size - 1
                    for (j in 0..lastIndexAnswers) {
                        answersBoolean[i].add(
                            ObservableField(
                                TextAndBoolean(
                                    response.tasks[i].answers[j].text,
                                    false
                                )
                            )
                        )
                    }
                }
                answers.addAll(answersBoolean)
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun startTime() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    time.postValue(time.value?.plus(1))
                    Thread.sleep(1000)
                }
            }
        }
    }

    fun getExam(): LiveData<ExamWithTaskForRetrieve> {
        return exam
    }
}