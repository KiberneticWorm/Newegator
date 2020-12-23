package ru.rubt.newegator.data.local

import android.content.Context
import android.util.Log
import androidx.core.util.LogWriter
import androidx.preference.PreferenceManager
import org.json.JSONObject
import ru.rubt.newegator.R
import java.util.*
import kotlin.collections.HashSet

class NewsSharedPreferences(private val ctx: Context) {

    companion object {

        const val KEY_LIST_SOURCES = "list_sources"
        const val KEY_DURATION = "list_durations"

        const val DURATION_DEFAULT = "5"
        const val ACCESS_KEY = "news_api_key"

    }

    val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)

    fun getNewsSources() : Array<String> {
        val newsSourcesId = prefs.getStringSet(KEY_LIST_SOURCES, hashSetOf())!!

        if (newsSourcesId.isEmpty()) {
            return ctx.resources.getStringArray(R.array.sources_id)
        }

        return newsSourcesId.toTypedArray()
    }

    fun getDuration() : Long =
            prefs.getString(
                    KEY_DURATION,
                    DURATION_DEFAULT
            )!!.toLong()

    fun getAccessKey(): String {
        val bytes = ctx.assets.open("keys.json").readBytes()
        val accessKeyJson = JSONObject(String(bytes))
        return accessKeyJson.getString(ACCESS_KEY)
    }

    fun isDarkTheme(): Boolean =
            prefs.getBoolean("is_dark_theme", false)


}