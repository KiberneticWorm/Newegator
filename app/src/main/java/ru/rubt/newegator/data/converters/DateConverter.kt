package ru.rubt.newegator.data.converters

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {
        const val TIME_DELIMITER = "T"
    }

    fun getDate(date: String) : String =
            date.split(TIME_DELIMITER)[0]

}