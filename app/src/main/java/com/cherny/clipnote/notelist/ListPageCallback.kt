package com.cherny.clipnote.notelist

import com.cherny.clipnote.entity.NoteItem

/**
 * Created by cherny on 1/3/18.
 */
interface ListPageCallback {
    fun onNoteQueried(noteSet:ArrayList<NoteItem>)
}