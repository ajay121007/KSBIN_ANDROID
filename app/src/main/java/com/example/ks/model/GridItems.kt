package com.example.ks.model

import com.example.ks.R
import com.example.ks.models.DashBoardResponse

class GridItems(var icons:Int,
                var girdText:String, var count:Int?=0) {
    companion object{

        fun getData(model:DashBoardResponse?): ArrayList<GridItems> {

            return arrayListOf<GridItems>(
                GridItems(R.drawable.ic_notes,"Signable \nDocuments",model?.data?.contractsCount),
                GridItems(R.drawable.ic_paymenticon,"Invoices &\nPayment",model?.data?.invoicesCount),
                GridItems(R.drawable.ic_idcards_icon,"ID Cards &\nDocuments",model?.data?.userDocumentsCount),
                GridItems(R.drawable.ic_policydoc_icon,"Add/Change/\nRemove-Driver\nVehicle, Plates"),
                GridItems(R.drawable.ic_uploaddocs_icon,"Submit Policy\nDocuments"),
                GridItems(R.drawable.ic_file_claim,"File a Claim"),
                GridItems(R.drawable.ic_file_claim,"Renewal"),

            )
        }

 }

    }
