package com.architecture.sample.domain.request

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.architecture.sample.data.model.NoteModel
import com.architecture.sample.domain.event.NoteEvent

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class NoteRequester : MviDispatcher<NoteEvent>() {

    override suspend fun onHandle(event: NoteEvent) {
        when (event) {
            is NoteEvent.GetNoteList -> {
                NoteModel.getNotes().collect {
                    sendResult(NoteEvent.GetNoteList(it))
                }
            }
            is NoteEvent.AddItem -> {
                NoteModel.insert(event.note!!)
                sendResult(event)
            }
            is NoteEvent.RemoveItem -> {
                NoteModel.delete(event.note!!)
                sendResult(event)
            }
            is NoteEvent.UpdateItem -> {
                NoteModel.update(event.note!!)
                sendResult(event)
            }
            is NoteEvent.MarkItem -> {
                NoteModel.update(event.note!!)
                sendResult(event)
            }
            is NoteEvent.ToppingItem -> {
                NoteModel.update(event.note!!)
                NoteModel.getNotes().collect {
                    sendResult(NoteEvent.GetNoteList(it))
                }
            }
        }
    }

}