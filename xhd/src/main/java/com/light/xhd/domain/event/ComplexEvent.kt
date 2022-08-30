package com.light.xhd.domain.event

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
sealed class ComplexEvent {
    data class ResultTest1(var count: Int = 0) : ComplexEvent()
    data class ResultTest2(var count: Int = 0) : ComplexEvent()
    data class ResultTest3(var count: Int = 0) : ComplexEvent()
    data class ResultTest4(var count: Int = 0) : ComplexEvent()
}
