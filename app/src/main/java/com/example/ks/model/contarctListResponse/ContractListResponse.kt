package com.example.ks.model.contarctListResponse

data class ContractListResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val valid: Boolean
)