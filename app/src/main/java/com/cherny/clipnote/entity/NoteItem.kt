package com.cherny.clipnote.entity

import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by cherny on 1/1/18.
 */
data class NoteItem(var id:Int, var title:String, var body:String, var date:String, var time:String) : Serializable{

    inner class BriefNote{
        var ID:Int = id
        var BODY:String = body
        var DATE:String = date
        var TIME:String = time

    }

    fun fromJson(json:String){
        val note = Gson().fromJson<NoteItem>(json,BriefNote::class.java)
        this.id = note.id
        this.body = note.body
        this.date = note.date
        this.time = note.time
        this.title = note.body.substringBefore("\n")
    }

    fun toJson(): String {

        return Gson().toJson(this.BriefNote())

    }
}