package com.cherny.clipnote.network

/**
 * Created by cherny on 1/3/18.
 */
object RequestURL {

    lateinit var HOST : String
    fun setHost(host:String) {
        HOST = host
    }

    val API_SAVE = "/notesave"
    val API_CHANGE = "/notechange"
    val APT_QUERY = "/notequery"
    val API_DELETE = "/notedelete"
    val API_PING = "/ping"
}
