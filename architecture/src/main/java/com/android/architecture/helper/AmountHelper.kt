package com.android.architecture.helper

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
     * 1.1 -> 000000000110
     */
    fun yuan2Fen12(yuan: Double): String {
        return String.format("%012d", yuan2Fen(yuan))
    }

    /**
     * 100000 -> 1,000.00
     */
    fun formatAmount(amount: Long): String {
        val amountString = amount.toString()
        val bigDecimal = BigDecimal(amountString)
        val decimalHelper = BigDecimal("100")
        val doubleValue = bigDecimal.divide(decimalHelper).toDouble()
        val decimalFormat = DecimalFormat("###,##0.00")
        return decimalFormat.format(doubleValue)
    }

    /**
     * 100000 -> 1000.00
     */
    fun formatAmountNoSymbols(amount: Long): String {
        val amountString = amount.toString()
        val bigDecimal = BigDecimal(amountString)
        val decimalHelper = BigDecimal("100")
        val doubleValue = bigDecimal.divide(decimalHelper).toDouble()
        val decimalFormat = DecimalFormat("0.00")
        return decimalFormat.format(doubleValue)
    }

    /**
     * 1000.0 -> 1,000.00
     */
    fun formatAmount(amount: Double): String {
        return formatAmount(yuan2Fen(amount))
    }

    /**
     * 1000.0 -> 1000.00
     */
    fun formatAmountNoSymbols(amount: Double): String {
        return formatAmountNoSymbols(yuan2Fen(amount))
    }

    /**
     * 10,001.00 -> 10001.0
     */
    fun convertAmount(amount: String): Double {
        val amt = amount.replace(",".toRegex(), "").toDouble()
        val decimalHelper = BigDecimal(amt.toString())
        return decimalHelper.setScale(2, RoundingMode.DOWN).toDouble()
    }

}