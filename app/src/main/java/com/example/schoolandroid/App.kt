package com.example.schoolandroid

import android.app.Application
import android.content.SharedPreferences
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.schoolandroid.data.RetrofitService
import com.example.schoolandroid.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        setTheme()
        configureRetrofit(getToken())
    }

    companion object {
        private lateinit var retrofitClient: RetrofitService
        private lateinit var app: Application

        suspend fun getService(): RetrofitService? {
            return if (Util.isNetworkAvailable(app.applicationContext)) {
                retrofitClient
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(app.applicationContext, app.getString(R.string.none_internet), Toast.LENGTH_LONG).show()
                }
                null
            }
        }

        public fun setTheme() {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(app)
            if (sharedPreference.getBoolean(app.getString(R.string.dark_theme_key), true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        private fun configureRetrofit(token: String?) {
            val httpLogging = HttpLoggingInterceptor()
            httpLogging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "${token}"
                        )

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
        public fun getToken(): String? {
            var token = getPreferences().getString(app.getString(R.string.token), null)
            token = if (token == null) {
                null
            } else {
                "Token ${token}"
            }
            return token
        }
        public fun getPreferences() =
            app.getSharedPreferences(app.getString(R.string.app_name), MODE_PRIVATE)

        public fun saveToken(token: String) {
            getPreferences()
                .edit()
                .putString(app.getString(R.string.token), token)
                .putBoolean(app.getString(R.string.isAuth), true)
                .apply()
            configureRetrofit(getToken())
        }
    }

}
