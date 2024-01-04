package com.kiudysng.ddproject.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiudysng.ddproject.R

class TagFragment : Fragment() {

    companion object {
        fun newInstance() = TagFragment()
    }

    private lateinit var viewModel: TagViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TagViewModel::class.java)
        // TODO: Use the ViewModel
    }

}