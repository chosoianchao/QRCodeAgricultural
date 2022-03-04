package com.utt.qrcodeagricultural.network.local

import android.content.Context
import com.utt.qrcodeagricultural.Constant.Companion.showToastShort
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.model.Agricultural
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgriculturalManager {

    fun insertData(agricultural: Agricultural, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDB.getInstance(context).dao.insertAgricultural(agricultural)
            withContext(Dispatchers.Main) {
                context.showToastShort(context.getString(R.string.add_success))
            }
        }
    }

    private suspend fun getAllItems(context: Context): List<Agricultural> =
        AppDB.getInstance(context).dao.getAll()

    fun deleteData(agricultural: Agricultural, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDB.getInstance(context = context).dao.deleteData(agricultural)
        }
    }

    fun searchName(search: String, listData: ArrayList<Agricultural>, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val agricultural = AppDB.getInstance(context = context).dao.searchName(search)
            agricultural.let { listData.addAll(it) }
        }
    }

    companion object {
        @JvmStatic
        var agriculturalManager: AgriculturalManager? = null
            get() {
                if (field == null) {
                    field = AgriculturalManager()
                }
                return field
            }
            private set
    }
}