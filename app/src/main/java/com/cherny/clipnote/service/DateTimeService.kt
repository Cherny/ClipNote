package com.cherny.clipnote.service

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by cherny on 1/22/18.
 */
object DateTimeService {

    @SuppressLint("SimpleDateFormat")
    fun getDateTime():String {

        val formatter = SimpleDateFormat("yyy/MM/dd")
        val curDate = Date(System.currentTimeMillis())
        val date = formatter.format(curDate)
        val time = SimpleDateFormat("HH:mm:ss").format(curDate)
        return date + " " +time
    }
}