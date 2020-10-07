package com.example.ks.models


import com.google.gson.annotations.SerializedName

data class LoginResponse(
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
        @SerializedName("token")
        val token: Token?,
        @SerializedName("user")
        val user: User?,
        @SerializedName("validations")
    val validations: List<ErrorResponse.Data.Validation?>?
    ) {
        data class Token(
            @SerializedName("access_token")
            val accessToken: String?,
            @SerializedName("expires_in")
            val expiresIn: Int?,
            @SerializedName("refresh_token")
            val refreshToken: String?,
            @SerializedName("token_type")
            val tokenType: String?
        )

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
            val managerName: Any?,
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