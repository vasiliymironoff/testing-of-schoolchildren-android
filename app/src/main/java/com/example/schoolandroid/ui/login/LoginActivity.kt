package com.example.schoolandroid.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.schoolandroid.App
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (App.getPreferences().getBoolean(this.getString(R.string.isAuth), false)) {
            App.getPreferences()
                .edit()
                .putBoolean(this.getString(R.string.isAuth), false)
                .apply()
        }

    }
}