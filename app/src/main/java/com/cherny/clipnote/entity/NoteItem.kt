package com.cherny.clipnote.entity

import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by cherny on 1/1/18.
 */
class NoteItem(var ID:Int, var BODY:String, var DATETIME:String) : Serializable{

    constructor( ) : this(-1, "", "")

    fun fromJson(json:String): NoteItem{
        val note = Gson().fromJson<NoteItem>(json,NoteItem::class.java) as NoteItem
        this.ID = note.ID
        this.BODY = note.BODY
        this.DATETIME = note.DATETIME

        return this
    }

    fun toJson(): String {

        return Gson().toJson(this)

    }

    fun getTitle(): String {
        return this.BODY.substringBefore('\n')
    }
}