package ru.rubt.newegator.data.converters

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class DateConverterTest {

    val dateConverter = DateConverter()

    @Test
    fun getDate() {
        val date1 = "2020-12-19T04:05:00Z"
        val date2 = "2020-12-18T04:03:06Z"
        val date3 = "2020-12-17T03:56:50Z"

        Assert.assertEquals("2020-12-19", dateConverter.getDate(date1))
        Assert.assertEquals("2020-12-18", dateConverter.getDate(date2))
        Assert.assertEquals("2020-12-17", dateConverter.getDate(date3))
    }
}