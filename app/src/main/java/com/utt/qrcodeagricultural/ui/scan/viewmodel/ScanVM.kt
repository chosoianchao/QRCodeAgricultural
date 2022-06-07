package com.utt.qrcodeagricultural.ui.scan.viewmodel

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.zxing.Result
import com.utt.qrcodeagricultural.Constant.Companion.AGRICULTURAL
import com.utt.qrcodeagricultural.Constant.Companion.NAME
import com.utt.qrcodeagricultural.base.BaseViewModel
import com.utt.qrcodeagricultural.model.Agricultural
import javax.inject.Inject

class ScanVM @Inject constructor() : BaseViewModel() {

    fun getAgricultural(it: Result, result: (result: Agricultural) -> Unit) {
        try {
            val getData = root?.child(AGRICULTURAL)
            getData?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapShot in snapshot.children) {
                        Log.d("Thang", "postSnapshot = $postSnapShot")
                        if (postSnapShot.child(NAME).value == it.toString()) {
                            val agricultural = postSnapShot.getValue<Agricultural>()
                            agricultural?.let { result -> result(result) }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
