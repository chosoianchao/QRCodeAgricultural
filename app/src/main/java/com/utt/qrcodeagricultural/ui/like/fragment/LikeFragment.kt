package com.utt.qrcodeagricultural.ui.like.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.utt.qrcodeagricultural.Constant.Companion.AGRICULTURAL
import com.utt.qrcodeagricultural.Constant.Companion.CANCEL
import com.utt.qrcodeagricultural.Constant.Companion.DELETE
import com.utt.qrcodeagricultural.Constant.Companion.DIALOG
import com.utt.qrcodeagricultural.Constant.Companion.INFORMATION
import com.utt.qrcodeagricultural.Constant.Companion.URI
import com.utt.qrcodeagricultural.OnActionCallBack
import com.utt.qrcodeagricultural.R
import com.utt.qrcodeagricultural.adapter.agricultural.AgriculturalAdapter
import com.utt.qrcodeagricultural.base.BaseFragment
import com.utt.qrcodeagricultural.databinding.LikeFragmentBinding
import com.utt.qrcodeagricultural.dialog.InformationDialog
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.ui.like.viewmodel.LikeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeFragment : BaseFragment<LikeFragmentBinding>(), OnActionCallBack {
    override val layoutResource: Int
        get() = R.layout.like_fragment
    override val viewModel: LikeVM by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LikeFragment.viewModel
        }
    }

    override fun initViews() {
        viewBinding.rvAgricultural.hasFixedSize()
        viewBinding.rvAgricultural.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        getData()
        searchAgricultural()
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.signOut()
                    requireActivity().finish()
                }
            })
    }

    private fun getData() {
        viewModel.getAll()
        val adapter = AgriculturalAdapter(this)
        viewBinding.rvAgricultural.adapter = adapter
        viewModel.agricultural.observe(viewLifecycleOwner) { agricultural ->
            adapter.submitList(agricultural)
        }
    }

    private fun searchAgricultural() {
        viewBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                viewModel.searchData(p0.toString())

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    private fun optionsAgricultural(agricultural: Agricultural) {
        val options = arrayOf<CharSequence>(INFORMATION, DELETE, CANCEL)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(AGRICULTURAL)
        builder.setItems(options) { dialog, items ->
            when {
                options[items] == INFORMATION -> {
                    showDialogInformation(agricultural)
                }
                options[items] == DELETE -> {
                    removeData(agricultural)
                }
                options[items] == CANCEL -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun removeData(agricultural: Agricultural) {
        viewModel.removeItems(agricultural)
    }

    private fun showDialogInformation(agricultural: Agricultural) {
        val dialogInfo = InformationDialog(context, agricultural, this)
        dialogInfo.show()
    }

    override fun callBack(data: Any?, key: String?) {
        if (key.equals(AGRICULTURAL)) {
            val agricultural: Agricultural = data as Agricultural
            optionsAgricultural(agricultural)
        } else if (key.equals(DIALOG)) {
            val agricultural: Agricultural = data as Agricultural
            val finalUrl: String = URI + agricultural.Name
            val uri: Uri = Uri.parse(finalUrl)
            val webIntent =
                Intent(Intent.ACTION_VIEW, uri)
            startActivity(webIntent)
        }
    }
}
