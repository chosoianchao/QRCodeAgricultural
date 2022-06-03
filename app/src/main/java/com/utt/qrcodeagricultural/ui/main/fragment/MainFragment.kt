package com.utt.qrcodeagricultural.ui.main.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.MainFragmentBinding
import com.utt.qrcodeagricultural.ui.main.viewmodel.MainVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.main_fragment
    override val viewModel: MainVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MainFragment.viewModel
        }
    }

    override fun initViews() {
        viewBinding.icon.setOnClickListener {
            val action: NavDirections =
                MainFragmentDirections.actionMainFragmentToScanFragment()
            findNavController().navigate(action)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.signOut()
                    requireActivity().finish()
                }
            })
    }
}