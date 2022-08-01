package com.android.architecture.helper

import java.util.*

object RandomHelper {

    fun getRandomNumberString(size: Int): String {
        val random = Random()
        val locale = Locale.getDefault()
        val sb = StringBuffer()
        while (sb.length < size) {
            val randomValue = random.nextInt(10)
            val hexString = Integer.toHexString(randomValue)
            sb.append(hexString)
        }
        return sb.toString().uppercase(locale)
    }

    fun getRandomHexString(size: Int): String {
        val random = Random()
        val locale = Locale.getDefault()
        val sb = StringBuffer()
        while (sb.length < size) {
            val randomValue = random.nextInt(16) // 0 - 16
            val hexString = Integer.toHexString(randomValue)
            sb.append(hexString)
        }
        return sb.toString().uppercase(locale)
    }

}