package ru.rubt.newegator.fragment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import ru.rubt.newegator.R
import ru.rubt.newegator.fragment.interfaces.ChangeThemeListener
import ru.rubt.newegator.fragment.interfaces.PrefSelectListener

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var prefSelectListener: PrefSelectListener
    lateinit var changeThemeListener: ChangeThemeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        prefSelectListener = context as PrefSelectListener
        changeThemeListener = context as ChangeThemeListener
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val listSourcePrefs =
                findPreference<MultiSelectListPreference>(KEY_LIST_SOURCES)

        listSourcePrefs?.setOnPreferenceChangeListener { _, newValue ->

            if ((newValue as Set<String>).isEmpty()) {
                prefSelectListener.onPrefSelect()
            }

            true
        }

        val switchToggleTheme =
                findPreference<SwitchPreferenceCompat>(KEY_IS_DARK_THEME)

        switchToggleTheme?.setOnPreferenceChangeListener { _, newValue ->
            val isDarkTheme = newValue as Boolean

            changeThemeListener.onChangeTheme(
                    if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO)

            true
        }
    }

    companion object {
        const val KEY_IS_DARK_THEME = "is_dark_theme"
        const val KEY_LIST_SOURCES = "list_sources"

        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}