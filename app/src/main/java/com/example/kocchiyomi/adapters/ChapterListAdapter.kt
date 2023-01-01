package com.example.kocchiyomi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kocchiyomi.R
import com.example.kocchiyomi.data.model.Chapter
import com.example.kocchiyomi.data.model.ScanlationGroupRelationship
import java.text.SimpleDateFormat

class ChapterListAdapter: RecyclerView.Adapter<ChapterListAdapter.ChapterListViewHolder>() {
    var chapterList: List<Chapter> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((Chapter) -> Unit)?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).
            inflate(R.layout.chapter_list_item, parent, false)
        view.clipToOutline = true
        return ChapterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        var chapterTitle = ""
        if (chapterList[position].attributes?.title != null && chapterList[position].attributes?.title?.isNotEmpty() == true){
                chapterTitle = "-" + (chapterList[position].attributes?.title ?: "")
        }

        var chapternumber = "X"
        if (chapterList[position].attributes?.chapter != "0" && chapterList[position].attributes?.chapter?.isNotEmpty() == true){
            chapternumber = chapterList[position].attributes?.chapter.toString()
        }

        holder.tv_chapter_title.text = holder.itemView.resources.getString(
            R.string.chapter_title,
            chapternumber,
            chapterTitle
        )

        val scanGroupRel: ScanlationGroupRelationship? =
            chapterList[position].relationships?.find { it.type == "scanlation_group" }

        var scanGroup = ""
        if (scanGroupRel != null)
            scanGroup = "• " + (scanGroupRel.attributes?.name ?: "")

        holder.tv_chapter_extra_info.text = holder.itemView.resources.getString(
            R.string.chapter_extra,
            SimpleDateFormat("dd/MM/yyyy").format(chapterList[position].attributes?.publishAt ?: ""),
            scanGroup
        )

        holder.itemView.setOnClickListener { onClick?.invoke(chapterList[position]) }
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    inner class ChapterListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_chapter_title: TextView
        var tv_chapter_extra_info: TextView

        init{
            tv_chapter_title = itemView.findViewById(R.id.tv_chapter_title)
            tv_chapter_extra_info = itemView.findViewById(R.id.tv_chapter_extra_info)
        }
    }
}