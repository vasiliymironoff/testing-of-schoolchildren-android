package com.example.schoolandroid.ui.profile

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Author
import com.example.schoolandroid.data.model.Avatar
import com.example.schoolandroid.data.model.Me
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ProfileViewModel : ViewModel() {

    private val currentUser = MutableLiveData<Me>()

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

    fun getCurrentUser(): LiveData<Me> {
        return currentUser
    }

    fun putAvatar(bitmap: Bitmap){
        try {
            viewModelScope.launch {
                val avatar = withContext(Dispatchers.Default) {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                    Avatar(Base64.encodeToString(byteArray, Base64.DEFAULT))
                }
                val response = withContext(Dispatchers.IO) {

                    App.getService()?.putProfile(currentUser.value?.id ?: -1, avatar)
                }
                if (response != null) {
                    val me = currentUser.value!!

                    me.avatar = response.avatar
                    currentUser.value = me
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
}