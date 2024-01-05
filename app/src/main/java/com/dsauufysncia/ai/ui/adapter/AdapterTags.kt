package com.dsauufysncia.ai.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dsauufysncia.ai.databinding.ItemTagLayoutBinding
import com.dsauufysncia.ai.utils.AiUtils

class AdapterTags :
    BaseQuickAdapter<String, AdapterTags.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: ItemTagLayoutBinding = ItemTagLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: String?) {
        holder.binding.tvTag.text = item
        holder.binding.root.setOnClickListener {
            AiUtils.copyToClipboard(context, item)
            Toast.makeText(context, "copy tag success!", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}