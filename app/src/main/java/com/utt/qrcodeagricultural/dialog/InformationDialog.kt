package com.utt.qrcodeagricultural.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import coil.load
import com.utt.qrcodeagricultural.Constant
import com.utt.qrcodeagricultural.Constant.Companion.DIALOG
import com.utt.qrcodeagricultural.OnActionCallBack
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.model.Agricultural

class InformationDialog(context: Context?, agricultural: Agricultural, callBack: OnActionCallBack) :
    Dialog(context!!) {
    @SuppressLint("SetTextI18n")
    private fun initViews(agricultural: Agricultural, callBack: OnActionCallBack) {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        val tvMore = findViewById<TextView>(R.id.tv_more)
        tvMore?.setOnClickListener {
            callBack.callBack(agricultural, DIALOG)
        }
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvPrice = findViewById<TextView>(R.id.tv_price)
        val tvDesc = findViewById<TextView>(R.id.tv_desc)
        val tvSupplier = findViewById<TextView>(R.id.tv_supplier)
        val tvDateOfManufacture = findViewById<TextView>(R.id.tv_dateOfManufacture)
        val tvExpiryDate = findViewById<TextView>(R.id.tv_expiryDate)
        val ivSupplier = findViewById<ImageView>(R.id.iv_imageSupplier)
        val iv = findViewById<ImageView>(R.id.iv_image)
        tvDesc?.text = "${context.getText(R.string.desc)}${agricultural.Desc}"
        tvName?.text = "${context.getText(R.string.name)}${agricultural.Name}"
        tvPrice?.text = "${context.getText(R.string.price)}${agricultural.Price}"
        tvSupplier?.text = "Sản xuất tại: ${agricultural.supplier}"
        tvDateOfManufacture?.text = "Ngày sản xuất: ${agricultural.dateOfManufacture}"
        tvExpiryDate?.text = "Ngày hết hạn: ${agricultural.expiryDate}"
        agricultural.Image?.let {
            val imgUri = it.toUri().buildUpon().scheme(Constant.HTTPS).build()
            iv.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image)
            }
        }
        agricultural.imageSupplier?.let {
            val imgUri = it.toUri().buildUpon().scheme(Constant.HTTPS).build()
            ivSupplier.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image)
            }
        }
    }

    init {
        setContentView(R.layout.data_dialog)
        initViews(agricultural, callBack)
    }
}