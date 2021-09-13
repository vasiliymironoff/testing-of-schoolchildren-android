package com.example.schoolandroid.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.App
import com.example.schoolandroid.data.model.EmailAndPassword
import com.example.schoolandroid.data.model.NewUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel : ViewModel() {
    private val success = MutableLiveData<Boolean>(false)

    fun registration(firstName: String,
                     lastName: String,
                     isTeacher: Boolean,
                     email: String,
                     password: String,
                     passwordRepeat: String) {
        if (password.trim() != passwordRepeat.trim()) {
            //
            return
        }
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.getService().registration(NewUser(firstName.trim(),
                        lastName.trim(),
                        isTeacher,
                        email.trim(),
                        password.trim()))
                }
                login(email.trim(), password.trim())
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }

        }
    }

    fun login(email: String, password: String) {
        try {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    App.getService().login(EmailAndPassword(email.trim(), password.trim()))
                }
                App.saveToken(result.token)
                success.value = true
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun getSuccess(): LiveData<Boolean> {
        return success
    }
}