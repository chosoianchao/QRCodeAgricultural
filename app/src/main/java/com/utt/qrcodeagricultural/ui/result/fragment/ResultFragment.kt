package com.utt.qrcodeagricultural.ui.result.fragment

import android.annotation.SuppressLint
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import coil.load
import com.utt.qrcodeagricultural.Constant.Companion.AGRICULTURAL
import com.utt.qrcodeagricultural.Constant.Companion.HTTPS
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.ResultFragmentBinding
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.ui.result.viewmodel.ResultVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : BaseFragment<ResultFragmentBinding>() {
    override val layoutResource: Int
        get() = R.layout.result_fragment
    override val viewModel: ResultVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ResultFragment.viewModel
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        val bundle = arguments
        val agricultural: Agricultural = bundle?.getSerializable(AGRICULTURAL) as Agricultural
        viewBinding.tvName.text = "${context?.getString(R.string.name)}${agricultural.Name}"
        viewBinding.tvPrice.text = "${context?.getString(R.string.price)}${agricultural.Price}"
        viewBinding.tvDesc.text = "${context?.getString(R.string.desc)}${agricultural.Desc}"
        agricultural.Image?.let {
            val imgUri = it.toUri().buildUpon().scheme(HTTPS).build()
            viewBinding.ivImage.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image)
            }
        }
        agricultural.imageSupplier?.let {
            val imgUri = it.toUri().buildUpon().scheme(HTTPS).build()
            viewBinding.ivImageSupplier.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image)
            }
        }
        viewBinding.tvDateOfManufacture.text = "Ng??y s???n xu???t: ${agricultural.dateOfManufacture}"
        viewBinding.tvExpiryDate.text = "Ng??y h???t h???n: ${agricultural.expiryDate}"
        viewBinding.tvSupplier.text = "S???n xu???t t???i: ${agricultural.supplier}"
        viewBinding.btLike.setOnClickListener {
            viewModel.insertValue(agricultural)
        }
    }
}
