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
    val strPrivacy =
        "Privacy Policy for Pagcor Picture Fortune\\nPrivacy Policyletv built the Pagcor Picture Fortune app as a Free app. This SERVICE is provided by letv at no cost and is intended for use as is.\n" +
                "\\nThis page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.\n" +
                "\\nIf you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\\nThe terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which are accessible at Pagcor Picture Fortune unless otherwise defined in this Privacy Policy.\n" +
                "\\nInformation Collection and Use\n" +
                "\\nFor a better experience, while using our Service, we may require you to provide us with certain personally identifiable information, including but not limited to letv. The information that we request will be retained by us and used as described in this privacy policy.\n" +
                "\\nThe app does use third-party services that may collect information used to identify you.\n" +
                "\\nLink to the privacy policy of third-party service providers used by the app\n" +
                "\\nGoogle Play Services\n" +
                "\\nLog Data\n" +
                "\\nWe want to inform you that whenever you use our Service, in a case of an error in the app we collect data and information (through third-party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (\\\"IP\\\") address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.\n" +
                "\\nCookies\n" +
                "\\nCookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                "\\nThis Service does not use these \\\"cookies\\\" explicitly. However, the app may use third-p"

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
        val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = strPrivacy
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