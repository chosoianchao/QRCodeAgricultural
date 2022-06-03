package com.utt.qrcodeagricultural.ui.register.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utt.qrcodeagricultural.Account
import com.utt.qrcodeagricultural.Constant.Companion.showToastShort
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.RegisterFragmentBinding
import com.utt.qrcodeagricultural.ui.register.viewmodel.RegisterVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.register_fragment
    override val viewModel: RegisterVm by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RegisterFragment.viewModel
        }
    }

    override fun initViews() {
        viewBinding.btRegister.setOnClickListener {
            validateAccount()
        }
        viewBinding.tvHaveAccount.setOnClickListener {
            goToLoginScreen()
        }
    }

    private fun validateAccount() {
        val name = viewBinding.edtName.text.toString()
        val email = viewBinding.edtEmail.text.toString()
        val password = viewBinding.edtPassword.text.toString()
        when (viewModel.validate(name, email, password)) {
            Account.ERROR_NAME -> context?.showToastShort("Hãy nhập tên của bạn")
            Account.ERROR_EMAIL -> context?.showToastShort("Hãy nhập email của bạn")
            Account.INVALID_EMAIL -> context?.showToastShort("Hãy nhập đúng định dạng email")
            Account.ERROR_PASSWORD -> context?.showToastShort("Hãy nhập mật khẩu và mật khẩu cần hơn 6 kí tự")
            else -> {
                viewBinding.progressBar.visibility = View.VISIBLE
                setCancelOutSideProgressBar()
                viewModel.register(name, email, password, ::registerSuccess, ::registerFailed)
            }
        }
    }

    private fun goToLoginScreen() {
        findNavController().popBackStack()
    }

    private fun registerFailed() {
        viewBinding.progressBar.visibility = View.GONE
        clearCancelOutSizeProgressBar()
        context?.showToastShort("Có gì đó không đúng \uD83D\uDE05")
    }

    private fun registerSuccess() {
        viewBinding.progressBar.visibility = View.GONE
        clearCancelOutSizeProgressBar()
        context?.showToastShort("Đăng kí thành công")
        val email = viewBinding.edtEmail.text.toString()
        val password = viewBinding.edtPassword.text.toString()

        val bundle: Bundle = Bundle().apply {
            putString("email", email)
            putString("password", password)
        }
        setBackStackData("bundleKey", bundle, true)
    }
}