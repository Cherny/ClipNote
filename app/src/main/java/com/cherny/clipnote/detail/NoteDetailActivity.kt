package com.cherny.clipnote.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.cherny.clipnote.R
import com.cherny.clipnote.entity.NoteItem
import com.cherny.clipnote.service.RemoteStore
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() , DetailPageCallback {

    enum class Mode (val data:Int) {
        SAVE(0),CHANGE(1),DELETE(2);

    }

    private lateinit var note: NoteItem
    private var storeState : Boolean = false
    private var editable : Boolean = false

    private lateinit var modeShift: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        this.note = this.intent.extras["note"] as NoteItem
        detail_note.setText(this.note.BODY)
        detail_date.text = this.note.DATETIME

        this.editable = (this.note.ID == -1)
        this.storeState = !this.editable
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        this.menuInflater.inflate(R.menu.note_detail,menu)

        if (menu != null) {
            this.modeShift = menu.findItem(R.id.detail_mode_shift)
        }

        this.shiftEditMode()

        return true
    }

    private fun shiftEditMode() {

        if (this.editable) {

            detail_note.isEnabled = true
            this.modeShift.setIcon(R.drawable.ic_mode_read)
        }
        else{

            detail_note.isEnabled = false
            this.modeShift.setIcon(R.drawable.ic_mode_edit)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId){

            R.id.detail_save -> {
                this.note.BODY = detail_note.text.toString().trim()
                this.note.DATETIME = detail_date.text.toString().trim()

                if (this.note.ID == -1) {
                    // save insert
                    RemoteStore.save(this.note,this)
                }
                else {
                    //change update
                    RemoteStore.change(this.note,this)
                }

                this.storeState = true
                this.editable = false
                this.shiftEditMode()

            }

            R.id.detail_mode_shift -> {

                if (!this.editable && this.storeState)
                    this.storeState = false

                this.editable = !this.editable
                this.shiftEditMode()

            }

            R.id.detail_delete -> {
                if (this.note.ID != -1) {
                    // save insert
                    RemoteStore.delete(this.note,this)
                }else {
                    this.finish()
                }
            }

            android.R.id.home -> {

                backConfirm()
            }
        }


        return true
    }

    override fun onBackPressed() {
        backConfirm()
    }

    private fun backConfirm() {

        if (this.storeState) {

            this.finish()
            return
        }

        var back: Int
        if (this.note.ID == -1)
            back = R.string.note_not_save
        else
            back = R.string.note_change_cancel

        AlertDialog.Builder(this)
                .setMessage(back)
                .setNegativeButton(R.string.cancle) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.confirm) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    this.finish()
                }
                .create()
                .show()
    }

    override fun onNoteChanged(id:Int,result: Boolean) {

        if (result)
            Toast.makeText(this, R.string.note_changed, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,R.string.note_store_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteStored(id: Int,result: Boolean) {

        if (result) {
            this.note.ID = id
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this,R.string.note_store_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteDeleted(id: Int, result: Boolean) {
        if (result) {
            val intent = Intent()
            intent.putExtra("id",this.note.ID)
            this.setResult(Mode.DELETE.data,intent)
            this.finish()
        }
        else
            Toast.makeText(this,R.string.note_delete_failed, Toast.LENGTH_SHORT).show()
    }
}
