package ru.rubt.newegator.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*
import ru.rubt.newegator.presenter.NewsPresenter

class TimerService: Service() {

    private var isTimerOn = false
    private var newsShowMode = ""
    private var timerDuration = 0L
    private var newsPresenter: NewsPresenter? = null

    companion object {
        private const val SECOND = 1000L
        const val MINUTE = SECOND * 60L

        const val TIMER_DURATION_EXTRA = "timer_duration"
        const val NEWS_SHOW_MODE_EXTRA = "news_show_mode"

    }

    inner class TimerServiceBinder: Binder() {
        fun getService(): TimerService = this@TimerService
    }

    private val timerBinder = TimerServiceBinder()

    override fun onBind(intent: Intent): IBinder {

        timerDuration = intent.getLongExtra(TIMER_DURATION_EXTRA, 0L)
        if (timerDuration == 0L) throw Exception("You must to put timerDuration parameter")
        newsShowMode = intent.getStringExtra(NEWS_SHOW_MODE_EXTRA) ?: throw Exception("You must to put newsShowMode parameter")

        isTimerOn = true
        timer()

        return timerBinder
    }

    override fun onUnbind(intent: Intent): Boolean {

        isTimerOn = false

        return super.onUnbind(intent)
    }

    fun setTimerDuration(timerDuration: Long) {
        this.timerDuration = timerDuration
    }

    fun setNewsShowMode(newsShowMode: String) {
        this.newsShowMode = newsShowMode
    }

    fun setNewsPresenter(newsPresenter: NewsPresenter) {
        this.newsPresenter = newsPresenter
    }

    private fun timer() {
        CoroutineScope(Dispatchers.IO).launch {
            while (isTimerOn) {
                delay(timerDuration * MINUTE)
                newsPresenter?.showNews(newsShowMode)
            }
        }
    }
}