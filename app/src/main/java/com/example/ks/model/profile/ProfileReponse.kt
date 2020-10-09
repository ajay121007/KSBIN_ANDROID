package com.example.ks.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileReponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("valid")
    val valid: Boolean?
) {
    data class Data(
        @SerializedName("user")
        val user: User?
    ) {
        data class User(
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("email")
            val email: String?,
            @SerializedName("email_sent")
            val emailSent: Int?,
            @SerializedName("email_verified_at")
            val emailVerifiedAt: Any?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("manager_name")
            val managerName: String?,
            @SerializedName("mobile_number")
            val mobileNumber: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("note")
            val note: Any?,
            @SerializedName("policy_from")
            val policyFrom: Any?,
            @SerializedName("policy_number")
            val policyNumber: String?,
            @SerializedName("policy_to")
            val policyTo: Any?,
            @SerializedName("role_id")
            val roleId: String?,
            @SerializedName("updated_at")
            val updatedAt: String?,
            @SerializedName("user_image")
            val userImage: String?,
            @SerializedName("user_image_url")
            val userImageUrl: String?
        )
    }
}