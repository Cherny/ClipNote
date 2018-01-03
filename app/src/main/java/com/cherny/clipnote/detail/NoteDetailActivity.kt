package com.cherny.clipnote.detail

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.cherny.clipnote.R
import com.cherny.clipnote.entity.NoteItem
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() , NoteStoreCallback{

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
        detail_note.setText(this.note.body)
        detail_date.text = this.note.time

        this.editable = this.note.id == -1
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

                if (this.note.id == -1) {

                    //save insert

                }
                else {

                    //change update

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
        if (this.note.id == -1)
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

    override fun onNoteChanged(result: Boolean) {

        if (result)
            Toast.makeText(this, R.string.note_changed, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,R.string.note_store_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteStored(id: Int) {

        this.note.id = id
        if (id != -1)
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,R.string.note_store_failed, Toast.LENGTH_SHORT).show()
    }
}
