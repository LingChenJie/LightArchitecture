package com.architecture.light.domain.event

import com.android.architecture.domain.dispatcher.event.BaseEvent

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/15
 * Modify date: 2022/11/15
 * Version: 1
 */
sealed class CommonMessages : BaseEvent() {
    object ToHomeTab : CommonMessages()
}