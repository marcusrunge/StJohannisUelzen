package com.marcusrunge.stjohannisuelzen.utils

import androidx.appcompat.app.AppCompatDelegate

/**
 * A utility object for managing the application's theme.
 *
 * This object provides a simple way to set the application's day/night theme
 * based on user preferences.
 */
object ThemeUtils {
    /**
     * Sets the application's day/night theme based on a string value.
     *
     * This function is designed to work with a preference setting where the user
     * can select a theme from a list. The theme is applied by setting the default
     * night mode for the entire application using [AppCompatDelegate.setDefaultNightMode].
     *
     * It is assumed that the `themeValues` array corresponds to the following modes:
     * - `themeValues[0]` -> System Default (`MODE_NIGHT_FOLLOW_SYSTEM`)
     * - `themeValues[1]` -> Light Theme (`MODE_NIGHT_NO`)
     * - `themeValues[2]` -> Dark Theme (`MODE_NIGHT_YES`)
     *
     * @param themeValues An array of strings representing the possible theme values,
     *                    typically from a `ListPreference`.
     * @param theme The currently selected theme value, which should be one of the
     *              strings from `themeValues`.
     */
    fun setTheme(themeValues: Array<String>, theme: String) {
        when (theme) {
            themeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            themeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            themeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
