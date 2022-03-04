package com.utt.qrcodeagricultural

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.utt.qrcodeagricultural.Constant.Companion.HTTPS


@BindingAdapter("image")
fun bindImage(imageView: ImageView, image: String?) {
    image?.let {
        val imageUri = image.toUri().buildUpon().scheme(HTTPS).build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.broken_image)
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("name")
fun TextView.bindName(name: String?) {
    text = "${context.getString(R.string.name)}$name"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("price")
fun TextView.bindPrice(price: String?) {
    text = "${context.getString(R.string.price)}$price"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("desc")
fun TextView.bindDesc(desc: String?) {
    text = "${context.getString(R.string.desc)}$desc"
}