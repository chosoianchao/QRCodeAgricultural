package com.utt.qrcodeagricultural.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.utt.qrcodeagricultural.Constant.Companion.DIALOG
import com.utt.qrcodeagricultural.OnActionCallBack
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.model.Agricultural

class InformationDialog(context: Context?, agricultural: Agricultural, callBack: OnActionCallBack) :
    Dialog(context!!) {
    @SuppressLint("SetTextI18n")
    private fun initViews(agricultural: Agricultural, callBack: OnActionCallBack) {
        val tvMore = findViewById<TextView>(R.id.tv_more)
        tvMore?.setOnClickListener {
            callBack.callBack(agricultural, DIALOG)
        }
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvPrice = findViewById<TextView>(R.id.tv_price)
        val tvDesc = findViewById<TextView>(R.id.tv_desc)
        tvDesc?.text = "${context.getText(R.string.desc)}${agricultural.Desc}"
        tvName?.text = "${context.getText(R.string.name)}${agricultural.Name}"
        tvPrice?.text = "${context.getText(R.string.price)}${agricultural.Price}"
    }


    init {
        setContentView(R.layout.data_dialog)
        initViews(agricultural, callBack)
    }
}