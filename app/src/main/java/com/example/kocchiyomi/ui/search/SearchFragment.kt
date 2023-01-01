package com.example.kocchiyomi.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kocchiyomi.R
import com.example.kocchiyomi.adapters.MangaListAdapter
import com.example.kocchiyomi.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.searchRecyclerView.setHasFixedSize(true)

        val adapter = MangaListAdapter()

        adapter.onClick = {
            val bundle = bundleOf(Pair("manga", it))
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mangaInfoFragment, bundle)

        }

        binding.searchRecyclerView.adapter = adapter

        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(searchQuery: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                if (searchQuery != null) {
                    viewModel.getSearchFeedFromMangadex(searchQuery)
                }
                return true
            }
        })

        viewModel.searchResponse.observe(viewLifecycleOwner) {
            response -> (binding.searchRecyclerView.adapter as MangaListAdapter).mangaList = response.data!!
        }

        super.onViewCreated(view, savedInstanceState)
    }

}