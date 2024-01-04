package com.kiudysng.ddproject.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kiudysng.ddproject.R
import com.kiudysng.ddproject.databinding.FragmentGeneratedPicBinding
import com.kiudysng.ddproject.net.AiApiImp.getTemplateStyle
import com.kiudysng.ddproject.ui.adapter.HomeTemplateStyleAdapter
import com.kiudysng.ddproject.ui.viewmodel.GeneratedPicViewModel
import com.kiudysng.ddproject.utils.BusAction
import com.kiudysng.ddproject.utils.RxBbs

class GeneratedPicFragment : Fragment() {

    companion object {
        fun newInstance() = GeneratedPicFragment()
    }

    private lateinit var viewModel: GeneratedPicViewModel
    private var binding: FragmentGeneratedPicBinding? = null
    private val adapter: HomeTemplateStyleAdapter by lazy {
        HomeTemplateStyleAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneratedPicBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GeneratedPicViewModel::class.java)
        initView()
    }

    private fun initView() {

        binding?.apply {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvTemplateStyle.layoutManager = linearLayoutManager
            rvTemplateStyle.setHasFixedSize(true)
            rvTemplateStyle.adapter = adapter
            PagerSnapHelper().attachToRecyclerView(rvTemplateStyle)
        }
        RxBbs.observeEvents(this) {
            when (it.action) {
                BusAction.BUS_ACTION_TEMPLATE_STYLE_SUCCESS -> {
                    viewModel.getTemplateStyle()
                }
            }
        }
        viewModel.apply {
            getTemplateStyle()
            templateStyles.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    binding?.rvTemplateStyle?.scrollToPosition(5)
                }
            }
        }
    }

}