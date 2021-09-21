package com.example.schoolandroid.ui.examdetail

import android.util.Log
import androidx.lifecycle.*
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
    private val editComment = MutableLiveData<Comment>()
    var isEdit = false
    fun fetchExam(id: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.getExam(id)
                }
                if (response != null) {
                    exam.value = response!!
                }

            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun sendComment(comment: CommentForPostAndPut, author: Author) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    if (isEdit) {
                        App.getService()?.putComment(
                            editComment.value!!.id,
                            CommentForPostAndPut(comment.exam, comment.text)
                        )
                    } else {
                        App.getService()?.postComment(comment)
                    }
                }

                if (response != null) {
                    if (!isEdit) {
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
                    } else {

                    }
                    isEdit = false
                }

            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun startEditComment(comment: Comment) {
        isEdit = true
        editComment.value = comment
    }


    fun getExam(): LiveData<ExamDetail> {
        return exam
    }

    fun getEditComment(): LiveData<Comment> {
        return editComment
    }
}