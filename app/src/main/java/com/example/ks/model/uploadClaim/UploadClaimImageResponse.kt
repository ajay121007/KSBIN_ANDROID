package com.example.ks.model.uploadClaim

data class UploadClaimImageResponse(
    val code: Int,
    val `data`: UploadClaimData,
    val message: String,
    val valid: Boolean
)