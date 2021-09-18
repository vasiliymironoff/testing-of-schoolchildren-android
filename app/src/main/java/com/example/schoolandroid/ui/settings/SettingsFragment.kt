package com.example.schoolandroid.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
import com.example.schoolandroid.App
import com.example.schoolandroid.R
import com.example.schoolandroid.ui.login.LoginActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val exitPreference = findPreference<Preference>(this.getString(R.string.settings_exit))
        exitPreference?.setOnPreferenceClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
            true
        }
        val themePreference = findPreference<Preference>(this.getString(R.string.dark_theme_key))
        themePreference?.setOnPreferenceClickListener {
            App.setTheme()
            true
        }
    }

}