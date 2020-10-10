package com.example.ks.model

import com.example.ks.R

class GridItems(var icons:Int,
                var girdText:String) {
    companion object{

        fun getData(): ArrayList<GridItems> {
            return arrayListOf<GridItems>(
                GridItems(R.drawable.ic_notes,"Signable \nDocumnets"),
                GridItems(R.drawable.ic_paymenticon,"Payment"),
                GridItems(R.drawable.ic_idcards_icon,"ID Cards &\nDocuments"),
                GridItems(R.drawable.ic_policydoc_icon,"Change my&\nPolicy Documents"),
                GridItems(R.drawable.ic_uploaddocs_icon,"Upload&\nPolicy Documents"),
                GridItems(R.drawable.ic_file_claim,"File a Claim"),

            )
        }

 }





    }
