package com.example.kocchiyomi.ui.browse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentBrowseBinding

class BrowseFragment : Fragment() {
    private lateinit var binding: FragmentBrowseBinding



    companion object {
        fun newInstance() = BrowseFragment()
    }

    private lateinit var viewModel: BrowseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {

    }

}