package com.marcusrunge.stjohannisuelzen.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    fun setTheme(themeValues: Array<String>, theme: String) {
        when (theme) {
            themeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            themeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            themeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}