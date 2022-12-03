package com.example.kocchiyomi.ui.reader

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kocchiyomi.R

class ReaderFragment : Fragment() {

    companion object {
        fun newInstance() = ReaderFragment()
    }

    private lateinit var viewModel: ReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reader, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReaderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}