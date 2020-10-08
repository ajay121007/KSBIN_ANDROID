package com.example.ks.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.databinding.PolicyCardLayoutBinding
import com.example.ks.models.DashBoardResponse

class PolicyAdapter : ListAdapter<DashBoardResponse.Data.Policy, PolicyAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding=DataBindingUtil.inflate<PolicyCardLayoutBinding>(LayoutInflater.from(parent.context),R.layout.policy_card_layout, parent, false)
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: PolicyAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(val binding:PolicyCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DashBoardResponse.Data.Policy) {
            binding.model=item
            binding.executePendingBindings()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<DashBoardResponse.Data.Policy>() {
    override fun areItemsTheSame(
        oldItem: DashBoardResponse.Data.Policy,
        newItem: DashBoardResponse.Data.Policy
    ): Boolean {
        return  oldItem.id==newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DashBoardResponse.Data.Policy,
        newItem: DashBoardResponse.Data.Policy
    ): Boolean {
        return oldItem.policyFromStr==newItem.policyFromStr
    }
}