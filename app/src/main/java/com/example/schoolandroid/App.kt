package com.example.schoolandroid

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.schoolandroid.data.RetrofitService
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    override fun onCreate()
    {
        super.onCreate()
        app = this
        setTheme()
        configureRetrofit()
    }

    companion object {
        private lateinit var retrofitClient: RetrofitService
        private lateinit var app: Application
        fun getService(): RetrofitService {
            return retrofitClient
        }
        private fun setTheme() {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(app)
            if (sharedPreference.getBoolean(R.string.dark_theme_key.toString(), false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        private fun configureRetrofit(){
            val httpLogging = HttpLoggingInterceptor()
            httpLogging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization",
                            " Token: ${getPreferences().getString(app.getString(R.string.token), "").orEmpty()}")
                        .build()
                    return@addInterceptor chain.proceed(request)
                }
                .addInterceptor(httpLogging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.31.123:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            retrofitClient = retrofit.create(RetrofitService::class.java)
        }

        public fun getPreferences() = app.getSharedPreferences(app.getString(R.string.app_name), MODE_PRIVATE)

        public fun saveToken(token: String) {
            getPreferences()
                .edit()
                .putString(app.getString(R.string.token), token)
                .putBoolean(app.getString(R.string.isAuth), true)
                .apply()
        }
    }

}
