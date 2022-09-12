package com.android.architecture.helper

import android.os.SystemClock
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @JvmStatic
    val MMDDYYYY: String
        get() = getCurrentDateFormatString("MMddyyyy")

    @JvmStatic
    val yearString: String
        get() = getCurrentDateFormatString("yyyy")

    @JvmStatic
    val monthDateString: String
        get() = getCurrentDateFormatString("MMdd")

    @JvmStatic
    val timeString: String
        get() = getCurrentDateFormatString("HHmm" + "ss")

    val currentUnixTimeMillisString: String
        get() {
            val millis = System.currentTimeMillis() / 1000
            return millis.toString()
        }

    val microTime: Long
        get() = System.currentTimeMillis() * 1000 + SystemClock.elapsedRealtimeNanos() / 1000 % 1000

    @JvmStatic
    fun getCurrentDateFormatString(pattern: String): String {
        val locale = Locale.getDefault()
        val millis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat(pattern, locale)
        val date = Date(millis)
        return dateFormat.format(date)
    }

    @JvmStatic
    fun getDateFormatString(
        millis: Long = System.currentTimeMillis(),
        pattern: String = "yyyy-MM-dd HH:mm:ss"
    ): String {
        val date = Date(millis)
        val locale = Locale.getDefault()
        val dateFormat = SimpleDateFormat(pattern, locale)
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