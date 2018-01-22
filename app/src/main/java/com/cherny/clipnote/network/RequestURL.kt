package com.cherny.clipnote.network

/**
 * Created by cherny on 1/3/18.
 */
object RequestURL {

    lateinit var HOST : String
    fun setHost(host:String) {
        HOST = host
    }

    val API_SAVE = "/noteSave.php"
    val API_CHANGE = "/noteChange.php"
    val APT_QUERY = "/noteQuery.php"
    val API_DELETE = "/noteDelete.php"
    val API_PING = "/ping.php"
}
