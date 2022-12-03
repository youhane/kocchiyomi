package com.example.kocchiyomi.ui.mangaInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kocchiyomi.R

class MangaInfoFragment : Fragment() {

    companion object {
        fun newInstance() = MangaInfoFragment()
    }

    private lateinit var viewModel: MangaInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manga_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MangaInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}