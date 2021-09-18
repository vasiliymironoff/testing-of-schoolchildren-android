package com.example.schoolandroid.ui.examdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Author
import com.example.schoolandroid.data.model.Comment
import com.example.schoolandroid.data.model.CommentForPostAndPut
import com.example.schoolandroid.data.model.ExamDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamDetailViewModel : ViewModel() {
    private val exam = MutableLiveData<ExamDetail>()

    fun fetchExam(id: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    App.getService().getExam(id)
                }
                exam.value = response
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun postComment(comment: CommentForPostAndPut, author: Author) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService().postComment(comment)
                }
                val newComment = Comment(
                    response.id,
                    author,
                    response.text,
                    response.publishTime,
                    response.editTime
                )
                val editExam = exam.value!!
                editExam.comments.add(newComment)
                exam.value = editExam
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun getExam(): LiveData<ExamDetail> {
        return exam
    }
}