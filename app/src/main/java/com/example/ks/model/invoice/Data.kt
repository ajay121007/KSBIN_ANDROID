package com.example.ks.model.invoice

data class Data(
    val created_at: String,
    val due: String,
    val id: Int,
    val invoice: String,
    val invoice_no: String,
    val paid: Int,
    val policy: String,
    val price: Int,
    val updated_at: String,
    val user_id: Int
)