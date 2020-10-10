package com.example.ks.model.documentid

data class DocumentModel(
    val created_at: String,
    val id: String,
    val invoice: String,
    val invoice_url: String,
    val name: String,
    val updated_at: String,
    val update_at_str: String,
    val user_id: Int
)