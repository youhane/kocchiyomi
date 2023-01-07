package com.example.kocchiyomi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kocchiyomi.R
import com.example.kocchiyomi.data.model.Manga

class MangaListAdapter : RecyclerView.Adapter<MangaListAdapter.MangaListViewHolder>(){

    var mangaList: List<Manga> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onClick:((Manga) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).
            inflate(R.layout.feed_list_item, parent, false)
        view.clipToOutline = true
        return MangaListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaListViewHolder, position: Int) {
        val mangaCover = mangaList[position].relationships?.firstOrNull{ rel -> rel.type == "cover_art" }?.attributes?.fileName
        if (mangaCover != null) {
            holder.iv_coverImage.load("https://uploads.mangadex.org/covers/${mangaList[position].id}/${mangaCover}.256.jpg")
        }

        holder.tv_titleName.text = mangaList[position].attributes?.title?.en ?: "No Title"
        holder.parentLayout.setOnClickListener {
            onClick?.invoke(mangaList[position])
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    inner class MangaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_coverImage: ImageView
        var tv_titleName: TextView
        var parentLayout: ConstraintLayout

        init {
            iv_coverImage = itemView.findViewById(R.id.iv_coverImage)
            tv_titleName = itemView.findViewById(R.id.tv_titleName)
            parentLayout = itemView.findViewById(R.id.feedLayout)
        }
    }
}