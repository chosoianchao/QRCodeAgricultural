package com.utt.qrcodeagricultural.ui.like.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utt.qrcodeagricultural.App
import com.utt.qrcodeagricultural.Coroutines
import com.utt.qrcodeagricultural.base.BaseViewModel
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.network.local.AgriculturalManager
import com.utt.qrcodeagricultural.network.local.AppDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class LikeVM @Inject constructor() : BaseViewModel() {
    private val _agricultural = MutableLiveData<List<Agricultural>>()
    val agricultural: LiveData<List<Agricultural>> = _agricultural
    private lateinit var job: Job

    fun getAll() {
        try {
            job = Coroutines.ioThenMain(
                { App.getInstance()?.let { AppDB.getInstance(it).dao.getAll() } },
                { _agricultural.value = it }
            )
        } catch (e: Exception) {
            _agricultural.value = listOf()
        }
    }

    fun searchData(msg: String) {
        job = Coroutines.ioThenMain(
            { App.getInstance()?.let { AppDB.getInstance(it).dao.searchName(msg) } },
            { _agricultural.value = it }
        )
    }


    fun removeItems(agricultural: Agricultural) {
        try {
            App.getInstance()
                ?.let {
                    AgriculturalManager.agriculturalManager?.deleteData(agricultural, it)
                }
            job = Coroutines.ioThenMain(
                { App.getInstance()?.let { AppDB.getInstance(it).dao.getAll() } },
                { _agricultural.value = it }
            )
        } catch (e: Exception) {
            _agricultural.value = listOf()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}