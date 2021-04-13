package com.furqoncreative.githubusers3.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.utils.DEFAULT_TIME
import com.furqoncreative.githubusers3.utils.cancelAlarm
import com.furqoncreative.githubusers3.utils.setRepeatingAlarm

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
        private var dailyReminder: SwitchPreference? = null
        private lateinit var KEY_REMINDER: String
        private lateinit var KEY_LANGUAGE: String

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            KEY_REMINDER = requireContext().getString(R.string.key_reminder)
            KEY_LANGUAGE = requireContext().getString(R.string.key_language)
            dailyReminder = findPreference<Preference>(KEY_REMINDER) as SwitchPreference?
            findPreference<Preference>(KEY_LANGUAGE)!!.onPreferenceClickListener = this
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?,
        ) {
            if (key == KEY_REMINDER) {
                val daily = sharedPreferences!!.getBoolean(KEY_REMINDER, false)
                dailyReminder?.isChecked = daily
                if (daily) {
                    setRepeatingAlarm(
                        requireContext(),
                        DEFAULT_TIME)
                } else {
                    cancelAlarm(requireContext())
                }
            }
        }

        override fun onPreferenceClick(preference: Preference?): Boolean {
            val key = preference!!.key
            if (key == KEY_LANGUAGE) {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }
            return false
        }

    }
}