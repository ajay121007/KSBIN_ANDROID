package com.example.ks.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.databinding.RenewalItemBinding
import com.example.ks.model.renewals.RenewalResponse.RenewalModel

class RenewalAdapter(private val onCLickOptions: OnCLickOptions) : ListAdapter<RenewalModel, RenewalAdapter.ItemViewholder>(RenewalCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding= DataBindingUtil.inflate<RenewalItemBinding>(
            LayoutInflater.from(parent.context),
                R.layout.renewal_item, parent, false)
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: RenewalAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewholder(val binding: RenewalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RenewalModel) {
            binding.handler=onCLickOptions
            binding.model=item.apply {
                price=price?:"0"
                option1=option1?:"0"
                monthly=monthly?:"0"
                monthly2=monthly2?:"0"
                fullPrice=fullPrice?:"0"
                option2=option2?:"0"
            }

            binding.executePendingBindings()
        }
    }
}
interface OnCLickOptions{
    fun onOptions1(id:RenewalModel,options: RenewalOptions)
}
enum class RenewalOptions{
    OPTIONS1,
    OPTIONS2,
    PAY_IN_FULL
}
class RenewalCallBack : DiffUtil.ItemCallback<RenewalModel>() {
    override fun areItemsTheSame(oldItem: RenewalModel, newItem: RenewalModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RenewalModel, newItem: RenewalModel): Boolean {
        return oldItem == newItem
    }
}
