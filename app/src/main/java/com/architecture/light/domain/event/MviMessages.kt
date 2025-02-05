package com.architecture.light.domain.event

import com.android.architecture.domain.dispatcher.event.BaseEvent

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
sealed class MviMessages : BaseEvent() {
    object RefreshNoteList : MviMessages()
    object FinishActivity : MviMessages()
}