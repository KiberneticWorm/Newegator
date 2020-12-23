package ru.rubt.newegator.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.rubt.newegator.App
import ru.rubt.newegator.R
import ru.rubt.newegator.data.NewsRepository
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.data.local.NewsSharedPreferences
import ru.rubt.newegator.presenter.NewsPresenter
import ru.rubt.newegator.view.NewsView
import javax.inject.Inject

class SplashActivity : MvpAppCompatActivity(), NewsView {

    @Inject lateinit var newsSharedPreferences: NewsSharedPreferences
    @Inject lateinit var newsRepository: NewsRepository
    @InjectPresenter lateinit var newsPresenter : NewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter() : NewsPresenter =
        NewsPresenter(newsRepository, newsSharedPreferences)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        newsPresenter.updateNews()
    }

    override fun loadedFailed(message: Int) {
        startMainActivity()
    }

    override fun loadedSuccess() {
        startMainActivity()
    }

    override fun showSuccess(news: List<NewsEntity>, showMode: String) {}

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}