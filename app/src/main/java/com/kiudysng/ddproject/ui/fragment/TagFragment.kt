package com.kiudysng.ddproject.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kiudysng.ddproject.R
import com.kiudysng.ddproject.databinding.FragmentTagBinding
import com.kiudysng.ddproject.ui.adapter.AdapterTags

class TagFragment : Fragment() {

    companion object {
        fun newInstance() = TagFragment()
    }

    private lateinit var viewModel: TagViewModel
    private val adapter: AdapterTags by lazy {
        AdapterTags()
    }

    private var binding: FragmentTagBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TagViewModel::class.java)
        initView()
    }

    fun initView() {
        val tagLists = resources.getStringArray(R.array.tag_lists).asList()
        adapter.submitList(tagLists)
        binding?.apply {
            rvTags.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvTags.adapter = adapter
        }
    }

}