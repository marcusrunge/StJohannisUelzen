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

/**
 * A [PreferenceFragmentCompat] that presents the app's settings.
 *
 * This fragment is responsible for:
 * - Displaying the settings from `R.xml.root_preferences`.
 * - Listening for changes in shared preferences and reacting accordingly.
 * - Handling theme changes.
 * - Managing push notification settings, including requesting permissions when necessary.
 */
@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Injected instance of the [Notification] interface, used for scheduling and canceling notifications.
     */
    @Inject
    lateinit var notification: Notification

    /**
     * An ActivityResultLauncher for requesting the `POST_NOTIFICATIONS` permission.
     * The callback checks which notification preferences are enabled and schedules the corresponding
     * notifications if the permission is granted.
     */
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                // After getting permission, check which notification preferences are enabled and schedule them.
                if (sharedPreferences.getBoolean(getString(R.string.key_newsfeed_pushnotifications), false)) {
                    notification.schedule.startRecurringNewsFeedNotification()
                }
                if (sharedPreferences.getBoolean(getString(R.string.key_dailymotto_pushnotifications), false)) {
                    notification.schedule.startRecurringDailyMotto()
                }
            }
        }

    /**
     * Sets the preferences from the XML resource.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @param rootKey If non-null, this preference fragment should be rooted at the [androidx.preference.PreferenceScreen] with this key.
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    /**
     * Registers the shared preference change listener when the fragment is resumed.
     */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Unregisters the shared preference change listener when the fragment is paused.
     */
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Called when a shared preference is changed, added, or removed.
     * This implementation handles changes to theme and notification settings.
     *
     * @param sharedPreferences The [SharedPreferences] that received the change.
     * @param key The key of the preference that was changed, added, or removed.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences == null || key == null) return

        when (key) {
            getString(R.string.key_theme) -> handleThemeChange(sharedPreferences, key)
            getString(R.string.key_dailymotto_pushnotifications),
            getString(R.string.key_newsfeed_pushnotifications) -> handleNotificationPreferenceChange(sharedPreferences, key)
        }
    }

    /**
     * Handles changes to the theme preference.
     * It retrieves the new theme value and applies it using [ThemeUtils].
     * @param preferences The [SharedPreferences] instance.
     * @param key The preference key for the theme setting.
     */
    private fun handleThemeChange(preferences: SharedPreferences, key: String) {
        val themeValues = resources.getStringArray(R.array.theme_values)
        val defaultTheme = themeValues.firstOrNull() ?: "default"
        val selectedTheme = preferences.getString(key, defaultTheme) ?: defaultTheme
        ThemeUtils.setTheme(themeValues, selectedTheme)
    }

    /**
     * Handles changes to notification-related preferences.
     *
     * If a notification preference is enabled, it checks for notification permissions (on Android Tiramisu and above).
     * If permission is granted, it schedules the notification. If not, it requests permission.
     * If a notification preference is disabled, it cancels the corresponding notification.
     *
     * @param preferences The [SharedPreferences] instance.
     * @param key The preference key for the notification setting.
     */
    private fun handleNotificationPreferenceChange(preferences: SharedPreferences, key: String) {
        val isEnabled = preferences.getBoolean(key, false)
        val startNotification: () -> Unit
        val stopNotification: () -> Unit

        when (key) {
            getString(R.string.key_dailymotto_pushnotifications) -> {
                startNotification = { notification.schedule.startRecurringDailyMotto() }
                stopNotification = { notification.schedule.stopRecurringDailyMotto() }
            }
            getString(R.string.key_newsfeed_pushnotifications) -> {
                startNotification = { notification.schedule.startRecurringNewsFeedNotification() }
                stopNotification = { notification.schedule.stopRecurringNewsFeedNotification() }
            }
            else -> return // Should not happen given the call site
        }

        if (isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                startNotification()
            }
        } else {
            stopNotification()
        }
    }
}