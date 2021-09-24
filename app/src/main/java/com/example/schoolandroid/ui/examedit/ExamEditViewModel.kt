package com.example.schoolandroid.ui.examedit

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.NewExam
import com.example.schoolandroid.data.model.NewTask
import com.example.schoolandroid.ui.examedit.model.ObservableAnswer
import com.example.schoolandroid.ui.examedit.model.ObservableTask
import com.example.schoolandroid.util.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ExamEditViewModel : ViewModel() {
    public val title = ObservableField("")
    public val classroom = ObservableField("")
    public val subject = ObservableField("")
    public val description = ObservableField("")
    private val tasks = MutableLiveData<ArrayList<ObservableTask>>()

    init {
        val list = ArrayList<ObservableTask>()
        list.add(getStandardTask())
        tasks.value = list
    }
    fun addTask() {

        val value = tasks.value
        value?.add(getStandardTask())
        tasks.value = value!!
        Log.e("TAG",tasks.value.toString())
    }

    private fun getStandardTask(): ObservableTask {
        val task = ObservableTask()
        task.answers.add(ObservableAnswer())
        task.answers.add(ObservableAnswer())
        return task
    }

    fun postExam(view: View): Boolean {
        val title = title.get()!!.trim()
        val classroom = classroom.get()
        val subject = subject.get()!!
        val description = description.get()!!.trim()
        if (title == "") {
            Snackbar.make(view, "Поле с названием не может быть пустым", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (description == "") {
            Snackbar.make(view, "Поле с описание не может быть пустым", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (subject == "") {
            Snackbar.make(view, "Поле с предметом не может быть пустым", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (classroom == "") {
            Snackbar.make(view, "Поле с классом не может быть пустым", Snackbar.LENGTH_SHORT).show()
            return false
        }

        for (task in tasks.value!!) {
            if (!task.isValid(view)) {
                return false
            }
        }
        try {
            viewModelScope.launch {
                val listTask = ArrayList<NewTask>()
                for (task in tasks.value!!) {
                    listTask.add(task.getTaskForPost())
                }
                val exam = NewExam(title,
                    classroom!!.toInt(),
                    description,
                    Util.getAbbreviationFromSubject(subject),
                    listTask)
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.postExamDetail(exam)
                }
            }
            return true
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
            return false
        }
    }

    fun getTasks(): LiveData<ArrayList<ObservableTask>> {
        return tasks
    }
}