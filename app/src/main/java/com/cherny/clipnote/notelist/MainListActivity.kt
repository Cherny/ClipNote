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
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : AppCompatActivity() {

    lateinit var adapter:MainListAdapter
    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        this.context = this
        this.adapter = MainListAdapter()
        mainlist_list.adapter = this.adapter
        mainlist_list.layoutManager = LinearLayoutManager(this.context)
        //mainlist_list.scrollToPosition( (mainlist_list.layoutManager as LinearLayoutManager)
        //        .findFirstCompletelyVisibleItemPosition())

        this.getDataSet()

        mainlist_sweeprefresh.setOnRefreshListener({
            Toast.makeText(this.context,"MainList refreshed",Toast.LENGTH_SHORT).show()

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
                        jumpToDetail(note)
                    }
                    .create()
                    .show()
        }
    }

    private fun jumpToDetail(note: NoteItem) {
        var intent:Intent = Intent()
        intent.setClass(context, NoteDetailActivity::class.java)
        intent.putExtra("note",note)
        context.startActivity(intent)
    }

    private fun getDataSet() {
        var note:ArrayList<NoteItem> = ArrayList()
        for (i in 1..10)
            note.add(NoteItem(i,"t$i","b$i","d$i"))
        this.adapter.addDataSet(note)
    }

    private fun loadMoreData(loadCount: Int) {
        var note:ArrayList<NoteItem> = ArrayList()
        val c = mainlist_list.adapter.itemCount
        for (i in c..c+loadCount)
            note.add(NoteItem(i,"t$i","b$i","d$i"))


        this.adapter.addDataSet(note)

        val count = this.adapter.itemCount
        Toast.makeText(context,"items counts:$count", Toast.LENGTH_SHORT).show()
        mainlist_list.adapter.notifyDataSetChanged()
    }
}
