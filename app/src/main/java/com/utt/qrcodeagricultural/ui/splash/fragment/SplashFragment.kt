package com.utt.qrcodeagricultural.ui.splash.fragment

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.Constant.Companion.TIME_DELAY
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.databinding.SplashFragmentBinding
import com.utt.qrcodeagricultural.ui.splash.viewmodel.SplashVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.splash_fragment
    override val viewModel: SplashVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SplashFragment.viewModel
        }
    }

    override fun initViews() {
        Handler(Looper.getMainLooper()).postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
            findNavController().navigate(action)
        }, TIME_DELAY.toLong())
    }
}