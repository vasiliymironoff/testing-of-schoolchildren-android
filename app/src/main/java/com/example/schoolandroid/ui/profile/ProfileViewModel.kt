package com.example.schoolandroid.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Author
import com.example.schoolandroid.data.model.Me
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProfileViewModel : ViewModel() {

    private val currentUser = MutableLiveData<Me>()

    public fun fetchMe() {
        try {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    App.getService().getMe()
                }
                currentUser.value = result
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun getCurrentUser(): LiveData<Me> {
        return currentUser
    }

    fun getUserForAuthor(): Author {
        return Author(
            currentUser.value!!.id,
            currentUser.value!!.firstName,
            currentUser.value!!.lastName,
            currentUser.value!!.avatar
        )
    }
}