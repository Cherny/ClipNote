package com.cherny.clipnote.detail

/**
 * Created by cherny on 1/3/18.
 */
interface DetailPageCallback {

    fun onNoteStored(id:Int,result: Boolean)
    fun onNoteChanged(id:Int,result: Boolean)
    fun onNoteDeleted(id:Int,result: Boolean)
}