package com.cherny.clipnote.entity

import java.io.Serializable

/**
 * Created by cherny on 1/1/18.
 */
data class ListItem(var id:Int, var title:String, var body:String, var time:String) : Serializable{

}