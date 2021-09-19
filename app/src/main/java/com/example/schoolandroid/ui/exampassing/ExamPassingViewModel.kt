package com.example.schoolandroid.ui.exampassing

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.ErrorStatistics
import com.example.schoolandroid.data.model.ExamWithTaskForRetrieve
import com.example.schoolandroid.data.model.StatisticsForPost
import com.example.schoolandroid.data.model.StatisticsForPut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamPassingViewModel : ViewModel() {

    private val exam = MutableLiveData<ExamWithTaskForRetrieve>()
    val time = MutableLiveData(0)
    val answers = ArrayList<List<ObservableField<TextAndBoolean>>>()
    private var idStatistics = MutableLiveData<Int>(-1)

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

    fun postStatistics(exam: Int, total: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    App.getService().postStatistics(StatisticsForPost(exam, 0, total))
                }
                idStatistics.value = response.id
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }

        }
    }

    fun checkWork(): ResultExam {
        var grade = exam.value!!.maxScores
        val errorsStatistics = ArrayList<ErrorStatistics>()
        val lastIndexTasks = exam.value!!.countTask - 1
        for (i in 0..lastIndexTasks!!) {
            val lastIndexAnswers = exam.value!!.tasks.size - 1
            for (j in 0..lastIndexAnswers) {
                if (exam.value!!.tasks[i].answers[j].isCorrect != answers[i][j].get()!!.check) {
                    grade -= exam.value!!.tasks[i].scores
                    errorsStatistics.add(ErrorStatistics(exam.value!!.tasks[i].question))
                    break
                }
            }
        }
        putStatistics(grade, errorsStatistics)
        return ResultExam(grade, exam.value!!.maxScores, errorsStatistics)

    }

    private fun putStatistics(grade: Int, errorsStatistics: List<ErrorStatistics>) {
        viewModelScope.launch {
            try {
                Log.e("TAG", "PUT")
                val response = withContext(Dispatchers.IO) {
                    App.getService().putStatistics(idStatistics.value!!, StatisticsForPut(exam.value!!.id, grade, 0, errorsStatistics))
                }
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