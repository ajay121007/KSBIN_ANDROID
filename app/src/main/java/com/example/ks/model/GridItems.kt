package com.example.ks.model

import com.example.ks.R

class GridItems(var icons:Int,
                var girdText:String) {
    companion object{

        fun getData(): ArrayList<GridItems> {
            return arrayListOf<GridItems>(
                GridItems(R.drawable.ic_notes,"Signable \nDocumnets"),
                GridItems(R.drawable.ic_notes,"Signable \nDocumnets")
            )
        }

 }





    }
