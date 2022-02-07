package com.marcusrunge.stjohannisuelzen.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var notification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.let {
            when (it) {
                getString(R.string.key_theme) -> sharedPreferences?.let { preferences ->
                    val themeValues = resources.getStringArray(R.array.theme_values)
                    preferences.getString(it, themeValues[0])?.let { theme ->
                        ThemeUtils.setTheme(themeValues, theme)
                    }
                }
                getString(R.string.key_pushnotifications) -> sharedPreferences?.let { preferences ->
                    if (preferences.getBoolean(it, false))
                        notification.schedule.startRecurringDailyMotto()
                    else
                        notification.schedule.stopRecurringDailyMotto()
                }
                else -> {

                }
            }

        }
    }

    override fun onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }
}