package com.example.ks.model.contarctListResponse

data class Data(
    val contract: String,
    val contractLink: Any,
    val contract_url: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val signed: Int,
    val updated_at: String,
    val userSign: String,
    val user_id: Int,
    val user_sign_url: String
)