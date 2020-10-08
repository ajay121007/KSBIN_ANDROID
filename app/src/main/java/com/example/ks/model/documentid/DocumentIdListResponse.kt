package com.example.ks.model.documentid

data class DocumentIdListResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val valid: Boolean
)