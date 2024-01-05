package com.dsauufysncia.ai.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dsauufysncia.ai.databinding.ActivityWebviewBinding

class LoadWebActivity:AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.run {

        }
    }
}