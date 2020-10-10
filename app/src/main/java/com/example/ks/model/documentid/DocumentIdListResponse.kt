package com.example.ks.model.documentid

data class DocumentIdListResponse(
    val code: Int,
    val `data`: List<DocumentModel>,
    val message: String,
    val valid: Boolean
)