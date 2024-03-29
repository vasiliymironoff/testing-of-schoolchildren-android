package com.example.schoolandroid.ui.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Author
import com.example.schoolandroid.data.model.Avatar
import com.example.schoolandroid.data.model.ExamForList
import com.example.schoolandroid.data.model.Me
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.net.URI

class ProfileViewModel : ViewModel() {

    private val currentUser = MutableLiveData<Me>()
    private val myExams = MutableLiveData<List<ExamForList>>()
    fun fetchMe() {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.getMe()
                }
                if (response != null) {
                    currentUser.value = response!!
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
    fun fetchMyExams() {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.getMyExams()
                }
                if (response != null) {
                    myExams.value = response!!
                }
            }
        } catch (e: Exception) {

        }
    }

    fun getCurrentUser(): LiveData<Me> {
        return currentUser
    }

    fun getMyExams(): LiveData<List<ExamForList>> {
        return myExams
    }

    fun putAvatar(bitmap: Bitmap, uri: Uri){

        try {
            viewModelScope.launch {
                val avatar = withContext(Dispatchers.Default) {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                    Avatar(Base64.encodeToString(byteArray, Base64.DEFAULT))
                }
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.putProfile(currentUser.value?.id ?: -1, avatar)
                }
                if (response != null) {
                    setNewAvatarForMyExam(uri.toString())
                    fetchMe()
                }
            }
        } catch (e: Exception) {
            Log.d("Tag", e.toString())
        }
    }

    fun getUserForAuthor(): Author {
        return Author(
            currentUser.value?.id,
            currentUser.value?.firstName,
            currentUser.value?.lastName,
            currentUser.value?.avatar
        )
    }

    fun setNewAvatarForMyExam(uri: String) {
        val exams = myExams.value!!
        for (exam in exams) {
            exam.author.avatar = uri
        }
        myExams.value = exams
    }
}