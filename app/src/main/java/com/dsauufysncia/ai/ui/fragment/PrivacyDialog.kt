package com.dsauufysncia.ai.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.dsauufysncia.ai.R
import com.dsauufysncia.ai.entity.BusEvent
import com.dsauufysncia.ai.utils.AiUtils
import com.dsauufysncia.ai.utils.BusAction
import com.dsauufysncia.ai.utils.RxBbs


class PrivacyDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_privacy_layout, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        val dm = DisplayMetrics();
        activity?.windowManager?.defaultDisplay?.getMetrics(dm);
        dialog?.window?.setLayout(
            dm.widthPixels - AiUtils.dpToPx(40.0f).toInt(),
            AiUtils.dpToPx(300.0f).toInt()
        );

    }

    private fun initView(view: View) {
        val ivClose = view.findViewById<TextView>(R.id.tv_i_agree)
        ivClose.setOnClickListener {
            if (isJump) {
                Intent(context, com.dsauufysncia.ai.MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
             RxBbs.postEvent(BusEvent(BusAction.BUS_ACTION_FINISH_SPLASH))
            dismissAllowingStateLoss()
        }
    }

    var isJump = false
    fun setData(isJump: Boolean) {
        this.isJump = isJump
    }

    interface IAgreeListener {
        fun iAgreeClick()
    }

    var iAgreeListener: IAgreeListener? = null
    fun setAgreeListener(agreeListener: IAgreeListener) {
        this.iAgreeListener = agreeListener
    }

//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        dialog?.window?.setGravity(Gravity.CENTER)
//    }
}