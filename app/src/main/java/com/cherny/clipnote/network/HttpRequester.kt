package com.cherny.clipnote.network

import com.cherny.clipnote.service.RemoteStore
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by cherny on 1/3/18.
 */
object HttpRequester {

    class Response {
        var id:Int = 0
        var result:Boolean = false
    }

    private val clent:OkHttpClient = OkHttpClient()

    fun doRequest(type:RemoteStore.StoreType, url:String, json:String) {

        val body = FormBody.Builder()
                .add("json",json)
                .build()
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val call = this.clent.newCall(request)

        val response = call.execute().body().toString()

        RemoteStore.responsed(type,response)
    }
}