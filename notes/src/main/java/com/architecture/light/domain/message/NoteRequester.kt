package com.architecture.light.domain.message

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.android.architecture.helper.Logger
import com.architecture.light.data.local.NoteModel
import com.architecture.light.data.local.db.entity.Note
import com.architecture.light.domain.event.NoteEvent

class NoteRequester : MviDispatcher<NoteEvent>() {

    override suspend fun onHandle(event: NoteEvent) {
        when (event) {
            is NoteEvent.GetNoteList -> {
                Logger.d(TAG, "GetNoteList")
                sendResult(NoteEvent.GetNoteList(NoteModel.getNotes()))
            }
            is NoteEvent.GetLockNoteList -> {
                Logger.d(TAG, "GetLockNoteList")
                sendResult(NoteEvent.GetLockNoteList(NoteModel.getLockNotes()))
            }
            is NoteEvent.AddItem -> {
                Logger.d(TAG, "AddItem")
                NoteModel.insert(event.note!!)
                sendResult(event)
            }
            is NoteEvent.RemoveItem -> {
                Logger.d(TAG, "RemoveItem")
                NoteModel.delete(event.note!!)
                sendResult(event)
            }
            is NoteEvent.UpdateItem -> {
                Logger.d(TAG, "UpdateItem")
                NoteModel.update(event.note!!)
                sendResult(event)
            }
            is NoteEvent.MarkItem -> {
                Logger.d(TAG, "MarkItem")
                NoteModel.update(event.note!!)
                sendResult(event)
            }
            is NoteEvent.ToppingItem -> {
                Logger.d(TAG, "ToppingItem")
                val update = NoteModel.update(event.note!!)
                if (update) {
                    sendResult(NoteEvent.GetNoteList(NoteModel.getNotes()))
                }
            }
            is NoteEvent.LockItem -> {
                Logger.d(TAG, "LockItem")
                NoteModel.update(event.note!!)
                sendResult(event)
            }
        }
    }

}