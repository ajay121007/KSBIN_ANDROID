package com.example.ks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.databinding.GridviewRowLayoutBinding
import com.example.ks.model.GridItems


class GridAdapter(val context: Context,private val onItemPositionClick: OnItemPositionClick) : BaseAdapter() {
    var gridList=GridItems.getData()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val grid= this.gridList[position]
        var inflator = LayoutInflater.from(context)
        val binding=DataBindingUtil.inflate<GridviewRowLayoutBinding>(inflator,R.layout.gridview_row_layout,parent,false)
        binding.gridImage.setImageResource(grid.icons)
        binding.gridTextView.text=grid.girdText
        binding.root.setOnClickListener {
            onItemPositionClick.onItem(getEnumFromPosition(position))
        }
        return binding.root

    }


    override fun getItem(position: Int): Any {
        return gridList[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()


    }


    override fun getCount(): Int {
        return gridList.size

    }
}
interface OnItemPositionClick{
    fun onItem(documents: Documents)
}

enum class Documents{
SIGNABLE_DOCS,
  PAYMENT,
    ID_CARDS,
    CHANGE_MY_DOCS,
    UPLOAD_DOCS,
    CLAIM_FILE
}
fun getEnumFromPosition(position: Int):Documents{
    return when(position){
        0-> Documents.SIGNABLE_DOCS
        1-> Documents.PAYMENT
        2-> Documents.ID_CARDS
        3-> Documents.CHANGE_MY_DOCS
        4-> Documents.UPLOAD_DOCS
        5-> Documents.CLAIM_FILE
        else -> Documents.SIGNABLE_DOCS
    }
}
