package com.utt.qrcodeagricultural.ui.scan.fragment

import android.Manifest
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.utt.qrcodeagricultural.Constant.Companion.AGRICULTURAL
import com.utt.qrcodeagricultural.Constant.Companion.showToastShort
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.ScanFragmentBinding
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.ui.scan.viewmodel.ScanVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : BaseFragment<ScanFragmentBinding>() {
    private var codeScanner: CodeScanner? = null

    override val layoutResource: Int
        get() = R.layout.scan_fragment
    override val viewModel: ScanVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ScanFragment.viewModel
        }
    }

    override fun initViews() {
        codeScanner = context?.let { CodeScanner(it, viewBinding.scannerView) }
        codeScanner?.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                viewBinding.progressBar.visibility = View.VISIBLE
                setCancelOutSideProgressBar()
            }
            viewModel.getAgricultural(it, ::getResult)
        }
    }

    private fun getResult(agricultural: Agricultural) {
        viewBinding.progressBar.visibility = View.GONE
        clearCancelOutSizeProgressBar()
        val bundle = bundleOf(
            AGRICULTURAL to agricultural
        )
        findNavController().navigate(R.id.action_scanFragment_to_resultFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        requestForCamera()
    }

    private fun requestForCamera() {
        Dexter.withContext(context).withPermission(Manifest.permission.CAMERA).withListener(object :
            PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                codeScanner?.startPreview()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                context.showToastShort(getString(R.string.permission_required))
                findNavController().popBackStack()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?,
            ) {
                p1?.continuePermissionRequest()
            }
        }).check()
    }
}