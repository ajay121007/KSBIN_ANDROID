package com.example.ks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.activities.payment.PaymentResponse
import com.example.ks.databinding.PaymentRowLayoutBinding

class PaymentAdapter(private val onPaymentItemClick: OnPaymentItemClick) : ListAdapter<PaymentResponse.PaymentModel, PaymentAdapter.ItemViewholder>(PaymentDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding = DataBindingUtil.inflate<PaymentRowLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.payment_row_layout,
            parent,
            false
        )

        return ItemViewholder(
            binding
        )
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewholder(val binding: PaymentRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaymentResponse.PaymentModel) {
            binding.model = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {  onPaymentItemClick.onClick(getItem(adapterPosition))}
        }
    }
}

class PaymentDiffCallBack : DiffUtil.ItemCallback<PaymentResponse.PaymentModel>() {
    override fun areItemsTheSame(
        oldItem: PaymentResponse.PaymentModel,
        newItem: PaymentResponse.PaymentModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PaymentResponse.PaymentModel,
        newItem: PaymentResponse.PaymentModel
    ): Boolean {
        return oldItem.createdAt == newItem.createdAt
    }

}
interface OnPaymentItemClick{
    fun onClick(model:PaymentResponse.PaymentModel)


}