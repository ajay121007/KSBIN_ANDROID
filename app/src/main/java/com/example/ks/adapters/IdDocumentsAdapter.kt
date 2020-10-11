package com.example.ks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.databinding.IdDocumentLayoutBinding
import com.example.ks.model.documentid.DocumentModel

class IdDocumentsAdapter(private val onDocumentDownload: OnDocumentDownload) :
    ListAdapter<DocumentModel, IdDocumentsAdapter.ItemViewholder>(IDCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding = DataBindingUtil.inflate<IdDocumentLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.id_document_layout,
            parent,
            false
        )
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: IdDocumentsAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewholder(val binding: IdDocumentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DocumentModel) {
            binding.model = item
            binding.executePendingBindings()
            binding.btnView.setOnClickListener { onDocumentDownload.onClick(getItem(adapterPosition).invoice_url) }
        }
    }
}

class IDCallBack : DiffUtil.ItemCallback<DocumentModel>() {
    override fun areItemsTheSame(oldItem: DocumentModel, newItem: DocumentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DocumentModel, newItem: DocumentModel): Boolean {
        return oldItem == newItem
    }

}

interface OnDocumentDownload {
    fun onClick(url: String)
}