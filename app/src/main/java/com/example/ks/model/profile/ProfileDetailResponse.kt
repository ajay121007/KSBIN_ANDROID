package com.example.ks.model.profile

data class ProfileDetailResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val valid: Boolean
)