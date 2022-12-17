package com.example.kocchiyomi.ui.reader

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kocchiyomi.R
import com.example.kocchiyomi.adapters.PageListAdapter
import com.example.kocchiyomi.databinding.FragmentMangaInfoBinding
import com.example.kocchiyomi.databinding.FragmentReaderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class ReaderFragment : Fragment() {
    private lateinit var binding: FragmentReaderBinding

    private val viewModel: ReaderViewModel by viewModels()
    private lateinit var chapterID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { chapterID = it.getString("chapter_id").toString() }
        viewModel.getChapterData(chapterID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReaderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PageListAdapter()
        val orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pagerView = binding.pagerView

        pagerView.adapter = adapter
        pagerView.orientation = orientation

        viewModel.response.observe(viewLifecycleOwner){
                response ->
            (pagerView.adapter as PageListAdapter).apiResponse = response
            (pagerView.adapter as PageListAdapter).pageList = response.chapterFiles.dataSaver
            (pagerView.adapter as PageListAdapter).notifyDataSetChanged()
        }
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout,pagerView ) { tab, position ->
            tab.text = (position + 1).toString()
        }.attach()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        (activity?.findViewById<BottomNavigationView>(R.id.bottom_nav))?.visibility = View.GONE
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        super.onResume()
    }

    override fun onStop() {
        (activity?.findViewById<BottomNavigationView>(R.id.bottom_nav))?.visibility = View.VISIBLE
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        super.onStop()
    }

}