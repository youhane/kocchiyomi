package com.example.kocchiyomi.ui.library

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kocchiyomi.KocchiyomiApplication
import com.example.kocchiyomi.R
import com.example.kocchiyomi.adapters.MangaListAdapter
import com.example.kocchiyomi.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding

    private val viewModel: LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
//            (activity?.application as KocchiyomiApplication).database.mangaDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getLibrary()
        binding = FragmentLibraryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.browseRecyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.browseRecyclerView.setHasFixedSize(true)

        val adapter = MangaListAdapter()

        binding.libraryFragment.setOnRefreshListener {
            viewModel.getLibrary()
            binding.libraryFragment.isRefreshing = false
        }
        adapter.onClick = {
            val bundle = bundleOf( Pair("manga", it), Pair("name", it.attributes?.title?.en) )
            Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_mangaInfoFragment, bundle)
        }

        binding.browseRecyclerView.adapter = adapter

        viewModel.libraryResponse.observe(viewLifecycleOwner){
                response -> (binding.browseRecyclerView.adapter as MangaListAdapter).mangaList = response
        }

        super.onViewCreated(view, savedInstanceState)
    }

}