package com.cherny.clipnote.network

import com.cherny.clipnote.service.RemoteStore
import okhttp3.*
import java.io.IOException

/**
 * Created by cherny on 1/3/18.
 */
object HttpRequester {

    class Response {
        var id:Int = 0
        var result:Boolean = false
    }

    private val client:OkHttpClient = OkHttpClient()

    private val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

    fun doRequest(type:RemoteStore.StoreType, url:String, json:String) {

        val body = FormBody.create(this.JSON,json)

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        this.client.newCall(request).enqueue(object :Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                RemoteStore.responsed(type,"")
            }

            override fun onResponse(call: Call?, response: okhttp3.Response?) {
                val result = response?.body()?.string()
                if (result != null) {
                    RemoteStore.responsed(type, result)
                }
            }
        })

    }

}