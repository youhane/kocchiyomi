package com.example.kocchiyomi.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kocchiyomi.R
import com.example.kocchiyomi.adapters.MangaListAdapter
import com.example.kocchiyomi.databinding.FragmentBrowseBinding

class BrowseFragment : Fragment() {
    private lateinit var binding: FragmentBrowseBinding

    private val viewModel: BrowseViewModel by activityViewModels {
        BrowseViewModelFactory(
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getFeed()
        binding = FragmentBrowseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        binding.browseRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.browseRecyclerView.setHasFixedSize(true)

        val adapter = MangaListAdapter()

        binding.browseFragment.setOnRefreshListener {
            viewModel.getFeed()
            binding.browseFragment.isRefreshing = false
        }

        adapter.onClick = {
            val bundle = bundleOf( Pair("manga", it))
            Navigation.findNavController(view).navigate(R.id.action_browseFragment_to_mangaInfoFragment, bundle)
        }

        binding.browseRecyclerView.adapter = adapter

        viewModel.feedResponse.observe(viewLifecycleOwner) {
            response -> (binding.browseRecyclerView.adapter as MangaListAdapter).mangaList = response.data!!
        }

        super.onViewCreated(view, savedInstanceState)
    }

}