package com.cherny.clipnote.detail

/**
 * Created by cherny on 1/3/18.
 */
interface NoteStoreCallback {

    fun onNoteStored(id:Int)
    fun onNoteChanged(result: Boolean)
}