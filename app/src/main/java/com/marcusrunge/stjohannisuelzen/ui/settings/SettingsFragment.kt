package com.marcusrunge.stjohannisuelzen.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var core: Core

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}