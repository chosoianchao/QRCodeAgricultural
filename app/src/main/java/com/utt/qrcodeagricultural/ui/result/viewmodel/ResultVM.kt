package com.utt.qrcodeagricultural.ui.result.viewmodel

import com.utt.qrcodeagricultural.App
import com.utt.qrcodeagricultural.base.BaseViewModel
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.network.local.AgriculturalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultVM @Inject constructor() : BaseViewModel() {

    fun insertValue(agricultural: Agricultural) {
        App.getInstance()
            ?.let {
                AgriculturalManager.agriculturalManager?.insertData(agricultural, it)
            }
    }
}