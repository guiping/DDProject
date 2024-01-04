package com.kiudysng.ddproject.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiudysng.ddproject.databinding.LayoutTemplateStyleBinding
import com.kiudysng.ddproject.entity.TemplateStyleEntityItem
import com.kiudysng.ddproject.ui.activity.CreateTaskActivity
import com.kiudysng.ddproject.utils.GlideUtils

class HomeTemplateStyleAdapter :
    BaseQuickAdapter<TemplateStyleEntityItem, HomeTemplateStyleAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: LayoutTemplateStyleBinding = LayoutTemplateStyleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: TemplateStyleEntityItem?) {
        holder.binding.apply {
            item?.let { item ->
                GlideUtils.loadImage(context, item.photo_url!!, ivTempPic)
                tvTemplateStyleName.text = "#${item.name}"
                tvTryNow.setOnClickListener {
                    Intent(context, CreateTaskActivity::class.java).apply {
                        putExtra("template_style", item)
                        context.startActivity(this)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

}