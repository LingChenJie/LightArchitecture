package com.architecture.light.domain.event

sealed class ComplexEvent {
    data class ResultTest1(val count: Int = 0) : ComplexEvent()
    data class ResultTest2(val count: Int = 0) : ComplexEvent()
    data class ResultTest3(val count: Int = 0) : ComplexEvent()
    data class ResultTest4(val count: Int = 0) : ComplexEvent()
}