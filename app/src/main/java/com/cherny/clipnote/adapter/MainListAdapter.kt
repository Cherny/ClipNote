package com.cherny.clipnote.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cherny.clipnote.R
import com.cherny.clipnote.entity.ListItem
import com.cherny.clipnote.listener.OnItemClickListener
import kotlinx.android.synthetic.main.widget_main_list_item.view.*


/**
 * Created by cherny on 1/1/18.
 */
class MainListAdapter  : RecyclerView.Adapter<MainListAdapter.VieWHolder>() , View.OnClickListener{


    private lateinit var itemClickListener : OnItemClickListener


    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener = listener
    }

    override fun onClick(view: View?) {

        this.itemClickListener.onItemClick(view,view?.tag as Int)

    }

    override fun onBindViewHolder(holder: VieWHolder?, position: Int) {

        Log.d("adapter","Element"+position+"set")
        holder?.getTitle()?.text = this.dataSet[position].title
        holder?.getDate()?.text = this.dataSet[position].time
        holder?.itemView?.tag = position

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VieWHolder {

        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.widget_main_list_item,parent,false)
        v.setOnClickListener(this)

        return VieWHolder(v)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun getItemData(position: Int) : ListItem {
        return this.dataSet[position]
    }

    private var dataSet : ArrayList<ListItem> = ArrayList()

    fun addDataSet( notes:ArrayList<ListItem>){

        this.dataSet.addAll(notes)
    }

    fun addSingleData(note:ListItem){
        this.dataSet.add(0,note)
    }

    fun removeSingleData(index:Int): Boolean{

        val size = this.dataSet.size
        this.dataSet.removeAt(index)
        return this.dataSet.size == (size - 1)

    }

    fun releaseAllData(): Boolean{
        this.dataSet.clear()

        return this.dataSet.size == 0
    }

    class VieWHolder(val v:View) : RecyclerView.ViewHolder(v) {

        private var title:TextView = v.main_list_item_title
        private var date:TextView = v.main_list_item_date

        fun getTitle():TextView{
            return title
        }

        fun getDate():TextView{
            return date
        }
    }
}