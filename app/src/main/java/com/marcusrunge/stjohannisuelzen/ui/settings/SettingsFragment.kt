package com.marcusrunge.stjohannisuelzen.ui.settings

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext())
                if(sharedPreferences.getBoolean(getString(R.string.key_newsfeed_pushnotifications), false))
                    notification.schedule.startRecurringNewsFeedNotification()
                if(sharedPreferences.getBoolean(getString(R.string.dailymotto_pushnotifications_header), false))
                    notification.schedule.startRecurringDailyMotto()
            }
        }

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
                getString(R.string.key_dailymotto_pushnotifications) -> sharedPreferences?.let { preferences ->
                    if (preferences.getBoolean(it, false)) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                            when (PackageManager.PERMISSION_GRANTED) {
                                context?.let { it1 ->
                                    ContextCompat.checkSelfPermission(
                                        it1,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                } -> {
                                    //
                                }

                                else -> {
                                    requestPermissionLauncher.launch(
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                }
                            }
                        } else
                            notification.schedule.startRecurringDailyMotto()
                    } else
                        notification.schedule.stopRecurringDailyMotto()
                }
                    getString(R.string.key_newsfeed_pushnotifications) -> sharedPreferences?.let { preferences ->
                    if (preferences.getBoolean(it, false)) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                            when (PackageManager.PERMISSION_GRANTED) {
                                context?.let { it1 ->
                                    ContextCompat.checkSelfPermission(
                                        it1,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                } -> {
                                    //
                                }

                                else -> {
                                    requestPermissionLauncher.launch(
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                }
                            }
                        } else
                            notification.schedule.startRecurringNewsFeedNotification()
                    } else
                        notification.schedule.stopRecurringNewsFeedNotification()
                }
                else -> {
                    //
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