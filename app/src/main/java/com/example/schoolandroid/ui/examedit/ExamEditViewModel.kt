package com.example.schoolandroid.ui.examedit

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
import java.io.FileDescriptor
import java.lang.Exception
import javax.security.auth.Subject

class ExamEditViewModel : ViewModel() {
    public var examId = 0
    public val title = ObservableField("")
    public val classroom = ObservableField("")
    public val subject = ObservableField("")
    public val description = ObservableField("")
    private val tasks = MutableLiveData<ArrayList<ObservableTask>>()

    private val result = MutableLiveData(false)

    fun getResult(): LiveData<Boolean> {
        return result
    }

    fun addTask() {
        val value = tasks.value
        value?.add(getStandardTask())
        tasks.value = value!!
        Log.e("TAG", tasks.value.toString())
    }

    private fun getStandardTask(): ObservableTask {
        val task = ObservableTask()
        task.answers.add(ObservableAnswer())
        task.answers.add(ObservableAnswer())
        return task
    }

    fun getExam(idExam: Int) {
        examId = idExam
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.getExamDetail(idExam)
                }
                if (response != null) {
                    title.set(response.title)
                    description.set(response.description)
                    classroom.set(response.classRoom.toString())
                    subject.set(Util.getSubjectFromAbbreviation(response.subject))
                    val taskList = ArrayList<ObservableTask>()
                    for (task in response.tasks) {
                        taskList.add(ObservableTask.getObservableTaskFromTask(task))
                    }
                    tasks.value = taskList
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
    fun putExam(view: View) {
        val title = title.get()!!.trim()
        val classroom = classroom.get()!!
        val subject = subject.get()!!
        val description = description.get()!!.trim()
        if (!validFields(view, title, description, subject, classroom)) {
            return
        }
        try {
            viewModelScope.launch {
                val listTask = ArrayList<NewTask>()
                for (task in tasks.value!!) {
                    listTask.add(task.getTaskForPost())
                }
                val exam = NewExam(
                    title,
                    classroom!!.toInt(),
                    description,
                    Util.getAbbreviationFromSubject(subject),
                    listTask
                )
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.putExamDetail(examId, exam)
                }
                result.value = true
            }

        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun postExam(view: View) {
        val title = title.get()!!.trim()
        val classroom = classroom.get()!!
        val subject = subject.get()!!
        val description = description.get()!!.trim()
        if (!validFields(view, title, description, subject, classroom)) {
            return
        }
        try {
            val response = viewModelScope.launch(Dispatchers.Main) {
                val listTask = ArrayList<NewTask>()
                for (task in tasks.value!!) {
                    listTask.add(task.getTaskForPost())
                }
                val exam = NewExam(
                    title,
                    classroom!!.toInt(),
                    description,
                    Util.getAbbreviationFromSubject(subject),
                    listTask
                )
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.postExamDetail(exam)
                }
                result.value = true
            }

        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun validFields(view: View, title: String, description: String, subject: String, classroom: String): Boolean {
        if (title == "") {
            Snackbar.make(view, "Поле с названием не может быть пустым", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if (description == "") {
            Snackbar.make(view, "Поле с описание не может быть пустым", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if (subject == "") {
            Snackbar.make(view, "Поле с предметом не может быть пустым", Snackbar.LENGTH_SHORT)
                .show()
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
        return true
    }
//    fun postTask() {
//        try {
//            viewModelScope.launch {
//                val response = withContext(Dispatchers.IO) {
//                    App.getService()?.postTask(TaskForPost("", 1, examId))
//                }
//                if (response != null) {
//                    val list = tasks.value
//                    list!!.add(ObservableTask.getObservableTaskFromTaskForPost(response))
//                    tasks.value = list!!
//                }
//                for (i in 0..1) {
//                    val responseAnswer = withContext(Dispatchers.IO) {
//                        App.getService()?.postAnswer(AnswerForPost("", false, response!!.id))
//                    }
//                    if (responseAnswer != null) {
//                        val list =
//                    }
//                }
//
//            }
//        } catch (e: Exception) {
//            Log.e("TAG", e.toString())
//        }
//    }

    fun deleteTask(id: Int) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = App.getService()?.deleteTask(id)
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }


    }

    fun deleteAnswer(id: Int) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = App.getService()?.deleteAnswer(id)
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun getTasks(): LiveData<ArrayList<ObservableTask>> {
        return tasks
    }

    fun initListTask() {
        val list = ArrayList<ObservableTask>()
        list.add(getStandardTask())
        tasks.value = list
    }

    fun reset() {
        title.set("")
        description.set("")
        initListTask()
    }

    fun deleteExam(examId: Int): Boolean {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.deleteExam(examId)
                }
            }
            return true
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
        return false
    }


}