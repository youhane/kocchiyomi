package com.example.kocchiyomi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kocchiyomi.data.api.ApiReaderResponse
import com.example.kocchiyomi.databinding.ReaderListItemBinding

class PageListAdapter(): RecyclerView.Adapter<PageListAdapter.PageListViewHolder>() {
    var pageList: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var apiResponse: ApiReaderResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageListViewHolder {
        val adapterLayout = ReaderListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PageListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PageListViewHolder, position: Int) {
        holder.loadImage("${apiResponse.baseUrl}/data-saver/${apiResponse.chapterFiles.hash}/${pageList[position]}")
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    inner class PageListViewHolder(private val binding: ReaderListItemBinding): RecyclerView.ViewHolder(binding.root){


        init{
            binding.btnRetry.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    loadImage("${apiResponse.baseUrl}/data-saver/${apiResponse.chapterFiles.hash}/${pageList[position]}")
                }
            }
        }

        fun loadImage(imageUrl: String) = binding.run {
            binding.ivImagePage.load(imageUrl) {
                listener(
                    onSuccess = { _, _ ->
                        groupError.isVisible = false
                        progressBar.isVisible = false
                    },
                    onError = { _, _ ->
                        groupError.isVisible = true
                        progressBar.isVisible = false
                    },
                    onStart = {
                        groupError.isVisible = false
                        progressBar.isVisible = true
                    }
                )
            }
        }
    }


}
