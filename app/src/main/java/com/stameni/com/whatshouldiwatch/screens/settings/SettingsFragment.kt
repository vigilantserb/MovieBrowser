package com.stameni.com.whatshouldiwatch.screens.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.stameni.com.whatshouldiwatch.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}