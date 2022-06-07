package com.utt.qrcodeagricultural.ui.login.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utt.qrcodeagricultural.Account
import com.utt.qrcodeagricultural.Admin
import com.utt.qrcodeagricultural.Constant.Companion.showToastShort
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.LoginFragmentBinding
import com.utt.qrcodeagricultural.ui.login.viewmodel.LoginVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.login_fragment
    override val viewModel: LoginVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }
    }

    override fun initViews() {
        getBackStackData("bundleKey", ::onResult)
        viewBinding.btLogin.setOnClickListener {
            val email = viewBinding.edtEmail.text.toString()
            val password = viewBinding.edtPassword.text.toString()
            when (viewModel.validate(email, password)) {
                Account.ERROR_EMAIL -> context.showToastShort("Vui lòng nhập email của bạn")
                Account.INVALID_EMAIL -> context.showToastShort("Hãy viết email hợp lệ")
                Account.ERROR_PASSWORD -> context.showToastShort("Xin hãy điền mật khẩu hoặc Mật khẩu cần 6 ký tự")
                Account.SUCCESS -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    setCancelOutSideProgressBar()
                    viewModel.login(
                        email,
                        password,
                        ::loginSuccess,
                        ::loginFailed,
                        ::login
                    )
                }

                else -> context.showToastShort("Hãy nhập các email và mật khẩu")
            }
        }
        viewBinding.tvDontHaveAccount.setOnClickListener {
            goToRegisterScreen()
        }
    }

    private fun login() {
        viewBinding.progressBar.visibility = View.GONE
        clearCancelOutSizeProgressBar()
        context?.showToastShort("Đăng nhập thành công")
        val action = LoginFragmentDirections.actionLoginFragmentToGenerateFragment()
        findNavController().navigate(action)
    }

    private fun onResult(bundle: Bundle) {
        val m = bundle.getString("email")
        val p = bundle.getString("password")
        viewBinding.edtEmail.setText(m)
        viewBinding.edtPassword.setText(p)
    }

    private fun goToRegisterScreen() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun loginFailed() {
        viewBinding.progressBar.visibility = View.GONE
        context?.showToastShort("Có gì đó không đúng \uD83D\uDE05")
    }

    private fun loginSuccess() {
        viewBinding.progressBar.visibility = View.GONE
        clearCancelOutSizeProgressBar()
        context?.showToastShort("Đăng nhập thành công")
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.accountExists(::accountExist)
    }

    private fun accountExist() {
        if (viewModel.validateAccount() == Admin.ADMIN) {
            val action = LoginFragmentDirections.actionLoginFragmentToGenerateFragment()
            findNavController().navigate(action)
        } else {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }
}