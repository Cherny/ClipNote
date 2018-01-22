package com.cherny.clipnote.service

import com.cherny.clipnote.detail.DetailPageCallback
import com.cherny.clipnote.entity.NoteItem
import com.cherny.clipnote.network.HttpRequester
import com.cherny.clipnote.network.RequestURL
import com.cherny.clipnote.notelist.ListPageCallback
import com.cherny.clipnote.setting.SetHostCallback
import com.google.gson.Gson
import com.google.gson.JsonParser

/**
 * Created by cherny on 1/3/18.
 */
object RemoteStore {


    fun responsed(type: StoreType, response:String) {

        when (type) {

            StoreType.SAVE -> {
                val re = Gson().fromJson<HttpRequester.Response>(response, HttpRequester.Response::class.java)
                this.storeCallback.onNoteStored(re.id, re.result)
            }

            StoreType.CHANGE -> {
                val re = Gson().fromJson<HttpRequester.Response>(response, HttpRequester.Response::class.java)
                this.storeCallback.onNoteChanged(re.id, re.result)
            }

            StoreType.QUERY -> {

                val noteSet: ArrayList<NoteItem> = ArrayList()
                if (response.isNotEmpty()) {
                    val array = JsonParser().parse(response).asJsonArray
                    array.map { it.asJsonObject.toString() }
                         .mapTo(noteSet) { NoteItem().fromJson(it) }
                }
                this.queryCallback.onNoteQueried(noteSet)
            }
            
            StoreType.DELETE -> {
                val re = Gson().fromJson<HttpRequester.Response>(response, HttpRequester.Response::class.java)
                this.storeCallback.onNoteDeleted(re.id, re.result)
            }

            StoreType.PING -> {

                if (response == "ping")
                    this.pingCallback.onResponse(true)
                else
                    this.pingCallback.onResponse(false)
            }

        }
    }

    enum class StoreType {
        SAVE,CHANGE,QUERY,DELETE,PING
    }

    private lateinit var storeCallback: DetailPageCallback
    private lateinit var queryCallback: ListPageCallback
    private lateinit var pingCallback:SetHostCallback

    fun save(note:NoteItem,callback: DetailPageCallback) {
        this.storeCallback = callback

        val url = RequestURL.HOST + RequestURL.API_SAVE
        HttpRequester.doRequest(StoreType.SAVE,url,note.toJson())
    }


    fun change(note: NoteItem,callback: DetailPageCallback) {
        this.storeCallback = callback

        val url = RequestURL.HOST + RequestURL.API_CHANGE
        HttpRequester.doRequest(StoreType.CHANGE, url, note.toJson())
    }

    fun query(index:Int,num:Int,callback: ListPageCallback) {
        this.queryCallback = callback

        val url = RequestURL.HOST + RequestURL.APT_QUERY
        val json = "{\"index\":$index,\"num\":$num}"
        HttpRequester.doRequest(StoreType.QUERY,url,json)
    }
    
    fun delete(note:NoteItem, callback: DetailPageCallback) {
    	this.storeCallback = callback
    	
    	val url = RequestURL.HOST + RequestURL.API_DELETE
    	HttpRequester.doRequest(StoreType.DELETE, url, note.toJson())
    }

    fun ping(host:String, callback: SetHostCallback) {
        this.pingCallback = callback

        val url = host + RequestURL.API_PING
        val json = "{\"code\":1}"
        HttpRequester.doRequest(StoreType.PING, url,json)
    }
}
