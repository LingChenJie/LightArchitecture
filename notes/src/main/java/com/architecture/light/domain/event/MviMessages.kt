package com.architecture.light.domain.event

sealed class MviMessages {
    object RefreshNoteList : MviMessages()
    object FinishActivity : MviMessages()
}