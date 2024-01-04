package com.kiudysng.ddproject.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class SettingDialogFragment: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_setting_layout, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initView(view)
        return view
    }
    private fun initView(view: View){
        val ivClose = view.findViewById<ImageView>(R.id.iv_close)
        val ivFeedback = view.findViewById<ImageView>(R.id.iv_feedback)
        val ivPrivacy = view.findViewById<ImageView>(R.id.iv_privacy)
        ivClose.setOnClickListener { dismissAllowingStateLoss() }
        ivPrivacy.setOnClickListener {
//            Intent(context,WebViewActivity::class.java).apply {
//                startActivity(this)
//            }
        }
        ivFeedback.setOnClickListener {
            sendEmail()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.TOP)
    }
    fun sendEmail() {
        val recipients = arrayOf("Esthermjackson@hotmail.com")
        val subject = "Contact Us"
        val body = " "
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            // 没有邮件应用可用，给出相应提示
            Toast.makeText(requireContext(), "No available email apps found", Toast.LENGTH_SHORT)
                .show()
        }
    }
}