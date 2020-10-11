package com.example.ks

import com.example.ks.utils.DateTimeUtils.getDaysLeft
import com.example.ks.utils.DateTimeUtils.getDifferenceDays
import com.example.ks.utils.DateTimeUtils.getDifferenceFroMCurrentDate
import com.example.ks.utils.DateTimeUtils.getProgressDuration
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val myFormat = SimpleDateFormat("YYYY-MM-DD")
        val parse = myFormat.parse("2020-09-01")
        val parse1 = myFormat.parse("2020-09-31")
        print("diff is" + getDaysLeft("2020-10-01","2020-10-31"))
//        assertEquals(4, 2 + 2)
    }
//    fun getDifferenceDays(d1: Date, d2: Date): Long {
//        val diff: Long = d2.time - d1.time
//        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
//    }

//    fun getCalendra(d1: Date){
//        return Calendar.getInstance().time=d1
//    }
//Convert Date to Calendar
  fun dateToCalendar(date: Date): Calendar? {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

    //Convert Calendar to Date
    private fun calendarToDate(calendar: Calendar): Date? {
        return calendar.time
    }
}