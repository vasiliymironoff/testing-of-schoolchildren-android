package com.example.schoolandroid

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.ui.profile.ProfileFragment
import com.example.schoolandroid.ui.profile.ProfileViewModel
import com.example.schoolandroid.ui.study.StudyViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_statistics, R.id.navigation_study, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        if (savedInstanceState == null) {
            viewModel.fetchMe()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!findNavController(R.id.nav_host_fragment_activity_main).popBackStack()) {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun hideUIForPerformanceExam() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.navView.visibility = View.GONE
    }

    fun showUIForPerformanceExam() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.visibility = View.VISIBLE
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("TAG", "Bitmap")
        if (requestCode == ProfileFragment.PICK && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            if (uri != null) {
                val inputStream = contentResolver?.openInputStream(uri)
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                viewModel.putAvatar(bitmap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    companion object {

        fun getSubjectFromAbbreviation(ab: String) =
            when (ab) {
                "al" -> "Алгебра"
                "as" -> "Астрономия"
                "bi" -> "Биология"
                "ch" -> "Химия"
                "en" -> "Английский"
                "gm" -> "Геометрия"
                "hi" -> "История"
                "ph" -> "Физика"
                "ru" -> "Русский язык"
                "cs" -> "Информатика"
                "ss" -> "Обществознание"
                "gg" -> "География"
                "fl" -> "Иностранный язык"
                "li" -> "Литература"
                "ob" -> "ОБЖ"

                else -> "Другой предмет"

            }
    }
}