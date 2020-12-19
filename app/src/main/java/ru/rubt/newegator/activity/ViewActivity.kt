package ru.rubt.newegator.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.rubt.newegator.App
import ru.rubt.newegator.R
import ru.rubt.newegator.data.NewsRepository
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.presenter.OneNewsPresenter
import ru.rubt.newegator.view.OneNewsView
import javax.inject.Inject

class ViewActivity : MvpAppCompatActivity(), OneNewsView {

    companion object {
        const val NEWS_ID_EXTRA = "newsId"
        const val NEWS_ID_EXTRA_DEFAULT = 0
    }

    @Inject lateinit var dateConverter: DateConverter
    @Inject lateinit var newsRepository: NewsRepository
    @InjectPresenter lateinit var oneNewsPresenter: OneNewsPresenter

    lateinit var ivImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvContent: TextView
    lateinit var tvPublishedAt: TextView
    lateinit var tvSource: TextView
    lateinit var root: View
    lateinit var floatActionIsRead: FloatingActionButton

    @ProvidePresenter
    fun provideNewsPresenter() : OneNewsPresenter =
            OneNewsPresenter(newsRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setTitle(R.string.view_activity_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        root = findViewById<View>(R.id.root)

        ivImage = findViewById(R.id.iv_image)
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
        tvPublishedAt = findViewById(R.id.tv_published_at)
        tvSource = findViewById(R.id.tv_source)
        floatActionIsRead = findViewById(R.id.floating_action_is_read)

        val newsId = intent.getIntExtra(NEWS_ID_EXTRA, NEWS_ID_EXTRA_DEFAULT)
        oneNewsPresenter.showOneNews(newsId)
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

    override fun showSuccess(oneNews: NewsEntity) {

        Glide.with(this)
                .load(oneNews.urlToImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(ivImage)

        tvTitle.text = oneNews.title
        tvContent.text = oneNews.description
        tvPublishedAt.text = dateConverter.getDate(oneNews.publishedAt)
        tvSource.text = oneNews.sourceName

        floatActionIsRead.setOnClickListener {
            oneNews.isRead = true
            oneNewsPresenter.updateNewsIsRead(oneNews)
            showSnackCancelIsRead(oneNews)
        }

    }

    private fun showSnackCancelIsRead(oneNews: NewsEntity) {

        val snackCancelIsRead = Snackbar.make(
            root, R.string.is_read, Snackbar.LENGTH_INDEFINITE)

        snackCancelIsRead.setAction(R.string.cancel_is_read) {
            oneNews.isRead = false
            oneNewsPresenter.updateNewsIsRead(oneNews)
            snackCancelIsRead.dismiss()
        }

        snackCancelIsRead.show()
    }
}