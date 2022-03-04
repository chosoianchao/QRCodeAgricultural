package com.utt.qrcodeagricultural.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    protected var viewBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(this).inflate(getLayOutId(), null)
        viewBinding = initViewBinding(view)
        setContentView(view)
        initViews()
    }

    protected abstract fun initViewBinding(view: View): B

    protected abstract fun getLayOutId(): Int

    protected abstract fun initViews()

}






