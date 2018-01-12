package com.cherny.clipnote

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test

/**
 * Created by cherny on 1/10/18.
 */
class HttpTest {


    data class User(var Account:String, var Passwd:String)


    private val JSON = MediaType.parse("application/json; charset=utf-8")

    private var client = OkHttpClient()

    @Test
    fun login() {
        val user = User("itachi", "123456789")
        val json = Gson().toJson(user)
        val url = "http://47.88.54.92/login"
//        val url = "http://localhost/ping.php"

        val body = FormBody.create(JSON,json)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful)
        {
            val result = response.body()?.string()
            System.out.println(result)
        }else{
            System.out.print("failure")
        }

        /*call.enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                 System.out.print("result failure")
            }

            override fun onResponse(call: Call?, response: Response?) {
                System.out.print("result" + response?.body().toString())
            }
        })*/
    }
}