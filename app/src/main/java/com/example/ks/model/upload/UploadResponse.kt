package com.example.ks.model.upload

data class UploadResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val valid: Boolean
)