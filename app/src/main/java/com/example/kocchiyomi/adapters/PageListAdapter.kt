package com.example.kocchiyomi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kocchiyomi.R

class PageListAdapter: RecyclerView.Adapter<PageListAdapter.PageListViewHolder>() {
    var pageList: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
//    lateinit var apiResponse: Api

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.page_list_item, parent, false)
        return PageListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PageListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    inner class PageListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var tv_chapter_title: TextView
//        var tv_chapter_extra_info: TextView

        init{
//            tv_chapter_title = itemView.findViewById(R.id.tv_chapter_title)
//            tv_chapter_extra_info = itemView.findViewById(R.id.tv_chapter_extra_info)
        }
    }


}
