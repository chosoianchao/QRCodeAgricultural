package com.utt.qrcodeagricultural.ui.generate.fragment

import android.text.TextUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.utt.qrcodeagricultural.Constant.Companion.showToastLong
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.GenerateFragmentBinding
import com.utt.qrcodeagricultural.ui.generate.viewmodel.GenerateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateFragment : BaseFragment<GenerateFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.generate_fragment
    override val viewModel: GenerateViewModel by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@GenerateFragment.viewModel
        }
    }

    override fun initViews() {
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.signOut()
                    requireActivity().finish()
                }
            })
        viewBinding.btGenerate.setOnClickListener {
            val content = viewBinding.edtContent.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                context?.showToastLong("You need enter content")
                return@setOnClickListener
            }
            viewBinding.ivQrCode.setImageBitmap(viewModel.getQrCodeBitmap(content))
        }
    }
}
