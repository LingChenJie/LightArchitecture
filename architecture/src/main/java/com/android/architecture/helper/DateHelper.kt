package com.android.architecture.helper

import android.os.SystemClock
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @JvmStatic
    val dateString: String
        get() = getDateFormatString("yyyy" + "MMdd")

    @JvmStatic
    val yearString: String
        get() = getDateFormatString("yyyy")

    @JvmStatic
    val monthDateString: String
        get() = getDateFormatString("MMdd")

    @JvmStatic
    val timeString: String
        get() = getDateFormatString("HHmm" + "ss")

    val currentUnixTimeMillisString: String
        get() {
            val millis = System.currentTimeMillis() / 1000
            return millis.toString()
        }

    val microTime: Long
        get() = System.currentTimeMillis() * 1000 + SystemClock.elapsedRealtimeNanos() / 1000 % 1000

    @JvmStatic
    fun getDateFormatString(
        pattern: String = "yyyy-MM-dd HH:mm:ss",
        millis: Long = System.currentTimeMillis(),
    ): String {
        val locale = Locale.getDefault()
        val dateFormat = SimpleDateFormat(pattern, locale)
        val date = Date(millis)
        return dateFormat.format(date)
    }

    /**
     * 获取指定时间的毫秒数 HH:mm:ss
     */
    @JvmStatic
    fun getTimeMillis(time: String): Long {
        try {
            val local = Locale.getDefault()
            val dateFormat = SimpleDateFormat("yy-MM-dd HH:mm:ss", local)
            val dayFormat = SimpleDateFormat("yy-MM-dd", local)
            val curDate = dateFormat.parse(dayFormat.format(Date()).toString() + " " + time)
            return curDate.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }


}