package com.android.architecture.helper

import android.os.SystemClock
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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
     * 获取指定时间的毫秒数
     * time:HH:mm:ss
     */
    @JvmStatic
    fun getTimeMillis(time: String): Long {
        try {
            val local = Locale.getDefault()
            val dayFormat = SimpleDateFormat("yyyy-MM-dd", local)
            return getTimeMillis(dayFormat.format(Date()).toString(), time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取指定时间的毫秒数
     * date:yyyy-MM-dd
     * time:HH:mm:ss
     */
    @JvmStatic
    fun getTimeMillis(date: String, time: String): Long {
        try {
            val local = Locale.getDefault()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", local)
            val curDate = dateFormat.parse("$date $time")
            if (curDate != null) {
                return curDate.time
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 本地时间差值
     *
     * @return 秒
     */
    fun getTimeDifference(): Long {
        val webTime = getWebTime()
        val localTime = System.currentTimeMillis()
        val time = webTime - localTime
        return abs(time) / 1000
    }

    /**
     * 获取网站的时间
     *
     * @return 毫秒
     */
    fun getWebTime(): Long {
        try {
            val url1 = URL("https://www.baidu.com/")
            val conn = url1.openConnection()
            conn.connect()
            val date = Date(conn.date)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val format = dateFormat.format(date)
            Logger.e("DateHelper", "getWebTime date:$format")
            return date.time
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }


}