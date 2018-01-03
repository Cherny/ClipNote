package com.cherny.clipnote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.cherny.clipnote.adapter.MainListAdapter
import com.cherny.clipnote.detail.NoteDetailActivity
import com.cherny.clipnote.entity.ListItem
import com.cherny.clipnote.listener.OnItemClickListener
import com.cherny.clipnote.listener.PullingLoadListener
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



        var list:ArrayList<ListItem> = ArrayList()
        for (i in 1..10)
            list.add(ListItem(i,"t$i","b$i","d$i"))
        this.adapter.addDataSet(list)

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

                var intent:Intent = Intent()
                intent.setClass(context, NoteDetailActivity::class.java)
                val data = adapter.getItemData(position)
                intent.putExtra("note",data)
                context.startActivity(intent)
            }

        })
    }

    private fun loadMoreData(loadCount: Int) {
        var list:ArrayList<ListItem> = ArrayList()
        val c = mainlist_list.adapter.itemCount
        for (i in c..c+loadCount)
            list.add(ListItem(i,"t$i","b$i","d$i"))


        this.adapter.addDataSet(list)

        val count = this.adapter.itemCount
        Toast.makeText(context,"items counts:$count", Toast.LENGTH_SHORT).show()
        mainlist_list.adapter.notifyDataSetChanged()
    }
}
