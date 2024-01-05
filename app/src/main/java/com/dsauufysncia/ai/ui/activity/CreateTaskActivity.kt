package com.dsauufysncia.ai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dsauufysncia.ai.R
import com.dsauufysncia.ai.databinding.ActivityCreateTaskBinding
import com.dsauufysncia.ai.entity.CreteTaskBodyEntity
import com.dsauufysncia.ai.entity.InputSpec
import com.dsauufysncia.ai.entity.TemplateStyleEntityItem
import com.dsauufysncia.ai.ui.viewmodel.MainViewModel
import com.dsauufysncia.ai.utils.GlideUtils

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private var templateStyleEntityItem: TemplateStyleEntityItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle == null) finish()
        bundle?.let {
            templateStyleEntityItem =
                it.getSerializable("template_style") as TemplateStyleEntityItem
        }
        templateStyleEntityItem?.let {
            GlideUtils.loadImage(this@CreateTaskActivity, it.photo_url!!, binding.ivTempPic)
        }
        viewModel.batchTaskSuccess.observe(this) {

            binding.flLoad.visibility = View.GONE
            //数据请求成功
            Intent(this@CreateTaskActivity, TaskFinishActivity::class.java).apply {
                putExtra("batch_img_url", it.result.final)
                putExtra("batch_msg_info", it.input_spec.prompt)
                startActivity(this)
                finish()
            }

        }
        binding.apply {
            val tagList = resources.getStringArray(R.array.tag_art_list).asList()
            flowTagLayout.addTags(tagList)
            ivBack.setOnClickListener { finish() }
            tvCreateTask.setOnClickListener {
                var inputMsg = edtInputMsg.text.toString()
                if (inputMsg.isEmpty()) {
                    makeText(
                        this@CreateTaskActivity,
                        "Please enter the description information to be generated",
                        LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                binding.flLoad.visibility = View.VISIBLE
                val tagLists = flowTagLayout.tagList
                val strBuild = StringBuilder()
                if (tagLists?.isNotEmpty() == true) {
                    for (tag in tagLists) {
                        strBuild.append("#").append(tag)
                    }
                }
                inputMsg += strBuild
                //组装数据
                val inputSpec =
                    InputSpec("RATIO_9_16", 0, 0, "NORMAL", inputMsg, templateStyleEntityItem?.id!!)

                val createTaskBodyEntity = CreteTaskBodyEntity(false, inputSpec)

                viewModel.createTask(createTaskBodyEntity)
            }
        }
    }

}
