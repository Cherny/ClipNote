package com.cherny.clipnote.service

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import com.cherny.clipnote.entity.NoteItem

/**
 * Created by cherny on 1/3/18.
 */
object ClipboardSevices {


    fun getNoteFromClipboard(context: Context) : NoteItem?{

        val body = this.getNoteBodyFromClipboard(context)

        if (body != null)
            return this.assembleNoteItem(body)
        return null
    }

    private fun getNoteBodyFromClipboard(context: Context) : String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

            val item =  clipboard.primaryClip.getItemAt(0)
            clipboard.primaryClip = ClipData.newPlainText(null,"")

            val text = item.text.toString()
            if (text.isNotEmpty())
                return  text
        }
        return null
    }

    private fun assembleNoteItem(body:String) : NoteItem {

        val dateTime = DateTimeService.getDateTime()

        return NoteItem(-1,body,dateTime)
    }
}