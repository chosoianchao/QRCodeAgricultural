package com.utt.qrcodeagricultural.adapter.agricultural

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utt.qrcodeagricultural.App
import com.utt.qrcodeagricultural.Constant.Companion.AGRICULTURAL
import com.utt.qrcodeagricultural.OnActionCallBack
import com.utt.qrcodeagricultural.databinding.AgriculturalViewBinding
import com.utt.qrcodeagricultural.model.Agricultural

class AgriculturalAdapter(val callBack: OnActionCallBack) :
    ListAdapter<Agricultural, AgriculturalAdapter.AgriculturalViewHolder>(DiffCallBack()) {

    class AgriculturalViewHolder private constructor(private val binding: AgriculturalViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agricultural: Agricultural) {
            binding.agricultural = agricultural
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AgriculturalViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AgriculturalViewBinding.inflate(layoutInflater, parent, false)
                return AgriculturalViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgriculturalViewHolder {
        return AgriculturalViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AgriculturalViewHolder, position: Int) {
        val agricultural = getItem(position)
        holder.bind(agricultural)
        holder.itemView.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(App.getInstance(),
                androidx.appcompat.R.anim.abc_popup_enter))
            callBack.callBack(agricultural, AGRICULTURAL)
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Agricultural>() {
    override fun areItemsTheSame(oldItem: Agricultural, newItem: Agricultural): Boolean {
        return oldItem.ID == newItem.ID
    }

    override fun areContentsTheSame(oldItem: Agricultural, newItem: Agricultural): Boolean {
        return oldItem == newItem
    }
}