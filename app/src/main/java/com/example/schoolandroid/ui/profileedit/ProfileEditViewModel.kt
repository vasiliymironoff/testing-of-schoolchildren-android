package com.example.schoolandroid.ui.profileedit


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.Email
import com.example.schoolandroid.data.model.Name
import com.example.schoolandroid.data.model.Password
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileEditViewModel : ViewModel() {
    companion object {
        const val NAME_OR_EMAIL = 1
        const val PASSWORD = 2
    }
    private val isEdit = MutableLiveData(0)

    fun getIsEdit(): LiveData<Int> {
        return isEdit
    }
    fun putName(first_name: String, last_name: String) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.putMe(Name(first_name.trim(), last_name.trim()))
                }
            }
            isEdit.value = NAME_OR_EMAIL
        }  catch(e: Exception) {
            Log.e("TAG", e.toString())
        }

    }

    fun postEmail(email: String, password: String) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.postEmail(Email(email.trim(), password.trim()))
                }
            }
            isEdit.value = NAME_OR_EMAIL
        }  catch(e: Exception) {
            Log.e("TAG", e.toString())
        }

    }

    fun postPassword(currentPassword: String, newPassword: String) {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    App.getService()?.postPassword(Password(currentPassword.trim(), newPassword.trim()))
                }
            }
            isEdit.value = PASSWORD
        }  catch(e: Exception) {
            Log.e("TAG", e.toString())
        }

    }
}