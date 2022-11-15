package com.architecture.light.domain.event

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
sealed class ComplexEvent {
    data class ResultTest1(val count: Int = 0) : ComplexEvent()
    data class ResultTest2(val count: Int = 0) : ComplexEvent()
    data class ResultTest3(val count: Int = 0) : ComplexEvent()
    data class ResultTest4(val count: Int = 0) : ComplexEvent()
}
git