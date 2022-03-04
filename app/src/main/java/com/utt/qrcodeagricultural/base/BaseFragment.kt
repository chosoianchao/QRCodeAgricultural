package com.utt.qrcodeagricultural.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.utt.qrcodeagricultural.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    private var _viewBinding: B? = null

    protected val viewBinding: B
        get() = _viewBinding ?: throw Exception(getString(R.string.system_error))

    @get:LayoutRes
    protected abstract val layoutResource: Int
    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = DataBindingUtil
        .inflate<B>(inflater, layoutResource, container, false)
        .apply { _viewBinding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    protected abstract fun initData()
    protected abstract fun initViews()
}