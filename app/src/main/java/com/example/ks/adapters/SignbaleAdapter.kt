package com.example.ks.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.databinding.SignableRowLayoutBinding

import com.example.ks.model.documentid.DocumentModel

class SignbaleAdapter(private val onContractItemClick: OnContractItemClick) : ListAdapter<DocumentModel, SignbaleAdapter.ItemViewholder>(SignCallBacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding=DataBindingUtil.inflate<SignableRowLayoutBinding>(LayoutInflater.from(parent.context),R.layout.signable_row_layout, parent, false)

        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: SignbaleAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

   inner class ItemViewholder(val binding: SignableRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DocumentModel) {
            binding.apply {
                model=item
                executePendingBindings()
                tvSignNow.setOnClickListener { onContractItemClick.onItemClick(ContractActions.SIGN,getItem(adapterPosition)) }
                buttonDownload.setOnClickListener { onContractItemClick.onItemClick(ContractActions.VIEW,getItem(adapterPosition)) }
            }

        }
    }
}

class SignCallBacks : DiffUtil.ItemCallback<DocumentModel>() {
    override fun areItemsTheSame(oldItem: DocumentModel, newItem: DocumentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DocumentModel,
        newItem: DocumentModel
    ): Boolean {
        return oldItem == newItem
    }
}

interface OnContractItemClick{
    fun onItemClick(actionType:ContractActions,item:DocumentModel)
}

enum class ContractActions{
    SIGN,
    VIEW
}