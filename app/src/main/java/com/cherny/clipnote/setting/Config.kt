package com.cherny.clipnote.setting

import android.content.Context
import com.cherny.clipnote.entity.Configuration
import com.google.gson.Gson
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * Created by cherny on 1/8/18.
 */
object Config {

    fun getConfiguration(context: Context) : Configuration? {

        val file = File(context.filesDir.absolutePath+"/config.json")
        if (!file.exists())
            return null
        val inStream = context.openFileInput("config.json")
        val reader = InputStreamReader(inStream,"utf-8")
        val data = CharArray(inStream.available())
        reader.read(data)
        reader.close()
        inStream.close()

        val jsonString = String(data)
        return Gson().fromJson(jsonString,Configuration::class.java)
    }

    fun saveConfiguration(context: Context, config:Configuration) {
        val outStream = context.openFileOutput("config.json",Context.MODE_PRIVATE)
        val writer = OutputStreamWriter(outStream,"utf-8")
        val jsonString = Gson().toJson(config)
        writer.write(jsonString)
        writer.flush()
        outStream.flush()
        writer.close()
        outStream.close()

    }
}