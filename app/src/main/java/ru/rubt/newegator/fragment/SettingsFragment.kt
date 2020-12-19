package ru.rubt.newegator.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import ru.rubt.newegator.R
import ru.rubt.newegator.interfaces.PrefSelectListener

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var listener: PrefSelectListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as PrefSelectListener
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val listSourcePrefs =
                findPreference<MultiSelectListPreference>(KEY_LIST_SOURCES)

        listSourcePrefs?.setOnPreferenceChangeListener { _, newValue ->

            if ((newValue as Set<String>).isEmpty()) {
                listener.onPrefSelect()
            }

            true
        }
    }

    companion object {
        const val KEY_LIST_SOURCES = "list_sources"

        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}