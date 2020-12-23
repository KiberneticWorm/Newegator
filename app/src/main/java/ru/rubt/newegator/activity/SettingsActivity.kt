package ru.rubt.newegator.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import ru.rubt.newegator.R
import ru.rubt.newegator.fragment.SettingsFragment
import ru.rubt.newegator.fragment.interfaces.ChangeThemeListener
import ru.rubt.newegator.fragment.interfaces.PrefSelectListener

class SettingsActivity : AppCompatActivity(), PrefSelectListener, ChangeThemeListener {

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setTitle(R.string.settings_activity_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        root = findViewById(R.id.root)

        val settingsFragment = SettingsFragment.newInstance()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, settingsFragment)
                .commit()
    }

    private  fun showAboutEmptySources() {
        val snackStatus = Snackbar.make(
                root, R.string.snack_list_sources_empty, Snackbar.LENGTH_LONG)

        snackStatus.show()
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_OK)
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrefSelect() {
        showAboutEmptySources()
    }
    override fun onChangeTheme(theme: Int) {
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}