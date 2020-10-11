package com.example.ks.utils

import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Created by skycap.
 */
object DateTimeUtils {
    fun getProgressDuration(d1: String?, d2: String?): Int {
        val totalDays=getDifferenceDays(d1,d2)
        val daysGone= getDifferenceFroMCurrentDate(d1)
        if(daysGone>totalDays)return 0
        return ((daysGone.toDouble()/totalDays)*100).toInt()
    }

    fun getDaysLeft(d1: String?, d2: String?): String {
        val totalDays=getDifferenceDays(d1,d2)
        val daysGone= getDifferenceFroMCurrentDate(d1)
        if(daysGone>totalDays)return "0"
        return (totalDays-daysGone).toString()
    }
    fun getDifferenceDays(d1: String?, d2: String?): Int {
        val date1= DateTime.parse(d1, DateTimeFormat.forPattern("YYYY-MM-dd"))
        val date2= DateTime.parse(d2, DateTimeFormat.forPattern("YYYY-MM-dd"))
        return Days.daysBetween(date1,date2).days
    }

    fun getDifferenceFroMCurrentDate(d1: String?): Int {
        return getDifferenceDays(d1,DateTime.now().toString("yyyy-MM-dd"))
    }
}