package com.utt.qrcodeagricultural

import android.content.Context
import android.widget.Toast

class Constant {
    companion object {
        fun Context?.showToastLong(message: String, length: Int = Toast.LENGTH_LONG) {
            Toast.makeText(this, message, length).show()
        }

        fun Context?.showToastShort(message: String, length: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, message, length).show()
        }

        const val HTTPS = "https"
        const val AGRICULTURAL = "Agricultural"
        const val INFORMATION = "Thông tin"
        const val DELETE = "Xóa"
        const val CANCEL = "Hủy"
        const val NAME = "Name"
        const val URI = "http://www.google.com/search?q="
        const val DIALOG = "Dialog"
        const val TIME_DELAY = 3000
    }
}