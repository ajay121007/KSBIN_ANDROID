package com.example.ks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ks.R
import com.example.ks.model.GridItems
import kotlinx.android.synthetic.main.gridview_row_layout.view.*

class GridAdapter : BaseAdapter {
    var gridList=GridItems.getData()
    var context: Context? = null

    constructor(context: Context?) : super() {

        this.context = context
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val grid= this.gridList[position]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var gridItemsView= inflator.inflate(R.layout.gridview_row_layout,null)
        gridItemsView.grid_image.setImageResource(grid.icons!!)
        gridItemsView.grid_textView.text=grid.girdText!!
        return gridItemsView

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