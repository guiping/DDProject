package com.kiudysng.ddproject.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiudysng.ddproject.R
import com.kiudysng.ddproject.ui.viewmodel.GeneratedPicViewModel

class GeneratedPicFragment : Fragment() {

    companion object {
        fun newInstance() = GeneratedPicFragment()
    }

    private lateinit var viewModel: GeneratedPicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generated_pic, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GeneratedPicViewModel::class.java)
        // TODO: Use the ViewModel
    }

}