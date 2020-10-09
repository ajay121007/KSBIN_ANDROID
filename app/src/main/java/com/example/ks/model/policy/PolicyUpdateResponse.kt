package com.example.ks.model.policy

data class PolicyUpdateResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val valid: Boolean
)