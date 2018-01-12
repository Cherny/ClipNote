package com.cherny.clipnote.service

/**
 * Created by cherny on 1/3/18.
 */
object RequestURL {

    lateinit var HOST : String
    fun setHost(host:String) {
        this.HOST = host
    }

    val API_SAVE = "/notesave"
    val API_CHANGE = "/notechange"
    val APT_QUERY = "/notequery"
    val API_PING = "/ping"
}