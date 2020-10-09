package com.example.ks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.databinding.SingleItemAdapterBinding
import com.example.ks.models.DashBoardResponse

class PolicyDropDownAdapter (arrayList: ArrayList<DashBoardResponse.Data.Policy>, val listener:PolicyListAdapterListener) :
    RecyclerView.Adapter<PolicyDropDownAdapter.ViewHolder>() {
    private var arrayList: ArrayList<DashBoardResponse.Data.Policy>


    init {
        setHasStableIds(true)
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleItemAdapterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.single_item_adapter, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position])
        holder.binding.txtCommonTextView.setOnClickListener {
            listener.onItemClickListener(arrayList[position])
        }



    }

    fun notifyAdapter(resultList: ArrayList<DashBoardResponse.Data.Policy>) {
        arrayList = resultList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    class ViewHolder(val binding: SingleItemAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DashBoardResponse.Data.Policy) {
            binding.txtCommonTextView.text = item.policyNumber


        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface PolicyListAdapterListener{
        fun onItemClickListener(itemData: DashBoardResponse.Data.Policy)
    }
}