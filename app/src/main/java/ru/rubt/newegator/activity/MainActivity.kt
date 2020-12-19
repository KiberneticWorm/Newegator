package ru.rubt.newegator.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.rubt.newegator.App
import ru.rubt.newegator.R
import ru.rubt.newegator.adapters.ExtendedNewsAdapter
import ru.rubt.newegator.adapters.StandardNewsAdapter
import ru.rubt.newegator.adapters.interfaces.ItemClickListener
import ru.rubt.newegator.data.NewsRepository
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.data.local.NewsSharedPreferences
import ru.rubt.newegator.presenter.NewsPresenter
import ru.rubt.newegator.services.TimerService
import ru.rubt.newegator.view.NewsView
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), NewsView {

    companion object {
        const val REQUEST_CODE_VIEW = 111
        const val REQUEST_CODE_SETTINGS = 222
    }

    @Inject lateinit var newsSharedPreferences: NewsSharedPreferences
    @Inject lateinit var newsRepository: NewsRepository
    @Inject lateinit var dateConverter: DateConverter
    @InjectPresenter lateinit var newsPresenter: NewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter() : NewsPresenter =
            NewsPresenter(newsRepository, newsSharedPreferences)

    private val timerServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as TimerService.TimerServiceBinder
            timerService = binder.getService()
            timerService.setNewsPresenter(newsPresenter)
            timerServiceIsBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) { timerServiceIsBound = false }
    }

    private var timerServiceIsBound = false
    private lateinit var timerService: TimerService
    private lateinit var rvNews : RecyclerView
    private lateinit var root: View
    private lateinit var spinnerModes : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        rvNews = findViewById(R.id.rv_news)
        rvNews.layoutManager = LinearLayoutManager(this)

        root = findViewById(R.id.root)

        spinnerModes = findViewById<AppCompatSpinner>(R.id.spinner_modes)
        spinnerModes.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showNews(position)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val newsShowMode = spinnerModes
                .getItemAtPosition(spinnerModes.selectedItemPosition)
                .toString()

        val intent = Intent(this, TimerService::class.java)
        intent.putExtra(TimerService.NEWS_SHOW_MODE_EXTRA, newsShowMode)
        intent.putExtra(TimerService.TIMER_DURATION_EXTRA, newsSharedPreferences.getDuration())
        bindService(intent, timerServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(timerServiceConnection)
        timerServiceIsBound = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_CODE_SETTINGS && timerServiceIsBound) {
            timerService.setTimerDuration(newsSharedPreferences.getDuration())
        }

        newsPresenter.updateNews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_SETTINGS)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getNewsShowMode(): String {
        val pos = spinnerModes.selectedItemPosition
        return spinnerModes
                .getItemAtPosition(pos)
                .toString()
    }

    private fun getCurrPos(): Int =
            (rvNews.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

    private fun showNews(pos: Int) {
        val newsShowMode = getNewsShowMode()

        if (timerServiceIsBound) {
            timerService.setNewsShowMode(newsShowMode)
        }
        newsPresenter.showNews(newsShowMode)
    }

    override fun showSuccess(news: List<NewsEntity>, showMode: String) {

        val adapter = if (showMode == getString(R.string.standard_mode))
            StandardNewsAdapter(this, news, dateConverter) else
            ExtendedNewsAdapter(this, news, dateConverter)

        adapter.setItemClickListener(object : ItemClickListener {
            override fun onNewsClick(newsId: Int) {
                startViewActivity(newsId)
            }
        })

        rvNews.adapter = adapter
    }

    override fun endLoaded() {
        showNews(spinnerModes.selectedItemPosition)
    }

    override fun showFailed(message: Int) {

        val newsShowMode = getNewsShowMode()

        val snackStatus = Snackbar.make(
                root, getString(message), Snackbar.LENGTH_LONG)

        snackStatus.setAction(R.string.snack_status_update) {
            newsPresenter.showNews(newsShowMode)
        }

        snackStatus.show()
    }

    private fun startViewActivity(newsId: Int) {
        val intent = Intent(this@MainActivity, ViewActivity::class.java)
        intent.putExtra(ViewActivity.NEWS_ID_EXTRA, newsId)
        startActivityForResult(intent, REQUEST_CODE_VIEW)
    }
}