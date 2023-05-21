package com.architecture.light.domain.event

import com.architecture.light.data.local.db.entity.Note

sealed class NoteEvent {
    var note: Note? = null
    fun setNote(note: Note): NoteEvent {
        this.note = note
        return this
    }

    data class GetNoteList(var notes: List<Note>? = null) : NoteEvent()
    data class GetLockNoteList(var notes: List<Note>? = null) : NoteEvent()
    object AddItem : NoteEvent()
    object RemoveItem : NoteEvent()
    object UpdateItem : NoteEvent()
    object MarkItem : NoteEvent()
    object ToppingItem : NoteEvent()
    object LockItem : NoteEvent()

}