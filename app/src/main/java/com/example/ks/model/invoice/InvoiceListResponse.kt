package com.example.ks.model.invoice

data class InvoiceListResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val valid: Boolean
)