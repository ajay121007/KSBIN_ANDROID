package com.example.ks.model.profile

data class User(
    val created_at: String,
    val email: String,
    val email_sent: Int,
    val email_verified_at: Any,
    val id: Int,
    val manager_name: Any,
    val mobile_number: String,
    val name: String,
    val note: Any,
    val policy_from: Any,
    val policy_number: String,
    val policy_to: Any,
    val role_id: String,
    val updated_at: String,
    val user_image: String,
    val user_image_url: String
)