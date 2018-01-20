package com.cherny.clipnote.service

import com.cherny.clipnote.detail.NoteStoreCallback
import com.cherny.clipnote.entity.NoteItem
import com.cherny.clipnote.network.HttpRequester
import com.cherny.clipnote.network.RequestURL
import com.cherny.clipnote.notelist.NoteQueryCallback
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
                this.storeCallback.onNoteStored(re.id)
            }

            StoreType.CHANGE -> {
                val re = Gson().fromJson<HttpRequester.Response>(response, HttpRequester.Response::class.java)
                this.storeCallback.onNoteChanged(re.result)
            }

            StoreType.QUERY -> {

                val array = JsonParser().parse(response).asJsonArray
                val noteSet: ArrayList<NoteItem> = ArrayList()

                array.mapTo(noteSet) { Gson().fromJson<NoteItem>(it, NoteItem::class.java) }
                this.queryCallback.onNoteQueried(noteSet)
            }
            
            StoreType.DELETE -> {

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

    private lateinit var storeCallback: NoteStoreCallback
    private lateinit var queryCallback:NoteQueryCallback
    private lateinit var pingCallback:SetHostCallback

    fun save(note:NoteItem,callback: NoteStoreCallback) {
        this.storeCallback = callback

        val url = RequestURL.HOST + RequestURL.API_SAVE
        HttpRequester.doRequest(StoreType.SAVE,url,note.toJson())
    }


    fun change(note: NoteItem,callback: NoteStoreCallback) {
        this.storeCallback = callback

        val url = RequestURL.HOST + RequestURL.API_CHANGE
        HttpRequester.doRequest(StoreType.CHANGE, url, note.toJson())
    }

    fun query(index:Int,num:Int,callback: NoteQueryCallback) {
        this.queryCallback = callback

        val url = RequestURL.HOST + RequestURL.APT_QUERY
        val json = "{\"index\":$index,\"num\":$num}"
        HttpRequester.doRequest(StoreType.QUERY,url,json)
    }
    
    fun delete(note:NoteItem, callback:NoteStoreCallback) {
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
