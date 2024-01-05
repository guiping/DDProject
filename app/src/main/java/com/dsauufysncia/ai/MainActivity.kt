package com.dsauufysncia.ai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dsauufysncia.ai.databinding.ActivityMainBinding
import com.dsauufysncia.ai.ui.adapter.ViewpagerAdapter2
import com.dsauufysncia.ai.ui.fragment.GeneratedPicFragment
import com.dsauufysncia.ai.ui.fragment.SettingDialogFragment
import com.dsauufysncia.ai.ui.fragment.TagFragment
import com.dsauufysncia.ai.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mFragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getData()

        binding.apply {
            viewModel.loadDialogState.observe(this@MainActivity) {
                if (it) {
                    flLoad.visibility = View.GONE
                } else {
                    flLoad.visibility = View.VISIBLE
                }
            }
            ivSetting.setOnClickListener {
                val settingDialog = SettingDialogFragment()
                if (settingDialog.isAdded && settingDialog.isVisible) {
                    settingDialog.dismiss()
                }
                settingDialog.show(supportFragmentManager, "settingDialog")
            }
            mFragments.add(GeneratedPicFragment.newInstance())
            mFragments.add(TagFragment.newInstance())
            viewpagerMain.isUserInputEnabled = false
            viewpagerMain.adapter = ViewpagerAdapter2(supportFragmentManager, mFragments, lifecycle)
            textGeneratedImg.isChecked = true
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    com.dsauufysncia.ai.R.id.text_generated_img -> {
                        viewpagerMain.currentItem = 0
                        title.text = getText(com.dsauufysncia.ai.R.string.art_work)
                    }

                    com.dsauufysncia.ai.R.id.radio_tag -> {
                        viewpagerMain.currentItem = 1
                        title.text = getText(com.dsauufysncia.ai.R.string.copy_tags)
                    }
                }
            }
        }
    }
}