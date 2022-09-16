package com.architecture.light.helper

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object AmountHelper {

    /**
     * 1.0 -> 1
     */
    fun fen2Fen(fen: Double): Long {
        val bigDecimal = BigDecimal(fen.toString())
        return bigDecimal.toLong()
    }

    /**
     * 1.1 -> 110
     */
    fun yuan2Fen(yuan: Double): Long {
        val bigDecimal = BigDecimal(yuan.toString())
        val decimalHelper = BigDecimal("100")
        return bigDecimal.multiply(decimalHelper).toLong()
    }

    /**
     * 100 -> 1.00
     */
    fun formatAmount(amount: Long): String {
        val amountString = amount.toString()
        val bigDecimal = BigDecimal(amountString)
        val decimalHelper = BigDecimal("100")
        val doubleValue = bigDecimal.divide(decimalHelper).toDouble()
        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(doubleValue)
    }

    /**
     * 1.0 -> 1.00
     */
    fun formatAmount(amount: Double): String {
        return formatAmount(yuan2Fen(amount))
    }

    /**
     * 10,001.00 -> 10001.00
     */
    fun convertAmount(amount: String): Double {
        val amt = amount.replace(",".toRegex(), "").toDouble()
        val decimalHelper = BigDecimal(amt)
        return decimalHelper.setScale(2, RoundingMode.DOWN).toDouble()
    }

}