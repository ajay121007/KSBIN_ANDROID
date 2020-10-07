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


class GridAdapter(val context: Context) : BaseAdapter() {
    var gridList=GridItems.getData()





    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val grid= this.gridList[position]
        var inflator = LayoutInflater.from(context)
        val binding=DataBindingUtil.inflate<GridviewRowLayoutBinding>(inflator,R.layout.gridview_row_layout,parent,false)
        binding.gridImage.setImageResource(grid.icons)
        binding.gridTextView.text=grid.girdText
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