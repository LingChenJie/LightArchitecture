package com.architecture.light.constant

import com.architecture.light.domain.event.ComplexEvent

/**
 * Created by SuQi on 2022/9/19.
 * Describe:
 */
sealed class TransResult
object TransSuccess : TransResult()
object TransFail : TransResult()
object TransUnknown : TransResult()
object TransSyncSuccess : TransResult()
object TransSyncFail : TransResult()


