package com.example.ks.model.forgot

data class ForgotPasswordResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val valid: Boolean
)