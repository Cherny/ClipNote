package com.cherny.clipnote.entity

import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by cherny on 1/1/18.
 */
data class NoteItem(var id:Int, var title:String, var body:String, var dateTime:String) : Serializable{

    inner class BriefNote{
        var ID:Int = id
        var BODY:String = body
        var DATETIME:String = dateTime

    }

    fun fromJson(json:String){
        val note = Gson().fromJson<NoteItem>(json,BriefNote::class.java) as BriefNote
        this.id = note.ID
        this.body = note.BODY
        this.dateTime = note.DATETIME
        this.title = note.BODY.substringBefore("\n")
    }

    fun toJson(): String {

        return Gson().toJson(this.BriefNote())

    }
}