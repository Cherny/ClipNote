package com.cherny.clipnote.notelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.cherny.clipnote.R
import com.cherny.clipnote.detail.NoteDetailActivity
import com.cherny.clipnote.entity.NoteItem
import com.cherny.clipnote.notelist.listener.OnItemClickListener
import com.cherny.clipnote.notelist.listener.PullingLoadListener
import com.cherny.clipnote.service.ClipboardSevices
import com.cherny.clipnote.service.RemoteStore
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : AppCompatActivity() , ListPageCallback {

    lateinit var adapter:MainListAdapter
    lateinit var context:Context
    lateinit var detailNote:NoteItem

    private enum class StartCode(val data:Int) {
        DETAIL(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        this.context = this
        this.adapter = MainListAdapter()
        mainlist_list.adapter = this.adapter
        mainlist_list.layoutManager = LinearLayoutManager(this.context)
        //mainlist_list.scrollToPosition( (mainlist_list.layoutManager as LinearLayoutManager)
        //        .findFirstCompletelyVisibleItemPosition())

        mainlist_sweeprefresh.setOnRefreshListener({
            Toast.makeText(this.context,"MainList refreshed",Toast.LENGTH_SHORT).show()
            this.getDataSet()
            mainlist_sweeprefresh.isRefreshing = false
        })

        mainlist_list.addOnScrollListener(object:PullingLoadListener(mainlist_list.layoutManager as LinearLayoutManager){

            override fun onLoadMore(loadCount: Int) {
                loadMoreData(loadCount)
            }
        })

        this.adapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(view: View?, position: Int) {

                val data = adapter.getItemData(position)
                detailNote = data
                jumpToDetail(data)
            }

        })
    }

    override fun onResume() {
        super.onResume()

        val note = ClipboardSevices.getNoteFromClipboard(this)

        if (note != null) {

            AlertDialog.Builder(this)
                    .setMessage(R.string.find_note)
                    .setNegativeButton(R.string.cancle) { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton(R.string.confirm) { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        detailNote = note
                        jumpToDetail(note)
                    }
                    .create()
                    .show()
        }
        this.getDataSet()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StartCode.DETAIL.data && resultCode == NoteDetailActivity.Mode.DELETE.data) {
            val index = data?.getIntExtra("index",-1)
            if (index != -1) {
                this.adapter.removeSingleData(index!!)
                mainlist_list.adapter.notifyDataSetChanged()
            }
        }
    }

    private fun jumpToDetail(note: NoteItem) {
        val intent = Intent()
        intent.setClass(context, NoteDetailActivity::class.java)
        intent.putExtra("note",note)
        this.startActivityForResult(intent,StartCode.DETAIL.data);
    }

    private fun getDataSet() {
        this.adapter.releaseAllData()
        RemoteStore.query(0,10,this)
    }

    private fun loadMoreData(loadCount: Int) {

        val index = mainlist_list.adapter.itemCount
        RemoteStore.query(index,loadCount,this)
    }

    override fun onNoteQueried(noteSet: ArrayList<NoteItem>) {

        if (noteSet.size <= 0) return
        this.adapter.addDataSet(noteSet)
        mainlist_list.adapter.notifyDataSetChanged()
    }


}
