package com.cherny.clipnote.network

import android.os.AsyncTask
import com.cherny.clipnote.service.RemoteStore
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by cherny on 1/3/18.
 */
object HttpRequester{

    private val client:OkHttpClient = OkHttpClient()
    private lateinit var type:RemoteStore.StoreType

//    private val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")



    fun doRequest(type:RemoteStore.StoreType, url:String, json:String) {

        this.type = type

//        val body = FormBody.create(this.JSON,json)
        val body = FormBody.Builder()
                .add("json",json)
                .build()

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        Requester(this.client,this.type).execute(request)
    }

    class Requester(private val client:OkHttpClient, private val type:RemoteStore.StoreType ):  AsyncTask<Request,Int, okhttp3.Response>() {
        override fun doInBackground(vararg request: Request): okhttp3.Response {

            return this.client.newCall(request[0]).execute()
        }

        override fun onPostExecute(response: okhttp3.Response?) {
            val body = response?.body()
            val result = body?.string()
            if (result != "-1" && result != null) {
                RemoteStore.responsed(this.type, result)
            }
        }
    }


    class Response {
        var id:Int = 0
        var result:Boolean = false
    }
}