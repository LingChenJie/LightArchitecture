package com.architecture.sample.domain.request

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.android.architecture.helper.Logger
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
                Logger.d(TAG, "GetNoteList")
                NoteModel.getNotes().collect {
                    sendResult(NoteEvent.GetNoteList(it))
                }
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
                NoteModel.update(event.note!!)
                NoteModel.getNotes().collect {
                    sendResult(NoteEvent.GetNoteList(it))
                }
            }
        }
    }

}