package com.architecture.light.domain.event

import com.android.architecture.domain.dispatcher.event.BaseEvent
import com.architecture.light.data.local.db.entity.Note

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
sealed class NoteEvent : BaseEvent() {
    var note: Note? = null
    fun setNote(note: Note): NoteEvent {
        this.note = note
        return this
    }

    data class GetNoteList(var notes: List<Note>? = null) : NoteEvent()
    object AddItem : NoteEvent()
    object RemoveItem : NoteEvent()
    object UpdateItem : NoteEvent()
    object MarkItem : NoteEvent()
    object ToppingItem : NoteEvent()

}