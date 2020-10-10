package com.example.ks.models


import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
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
        @SerializedName("access_token")
        val accessToken: String?,
        @SerializedName("expires_in")
        val expiresIn: Int?,
        @SerializedName("refresh_token")
        val refreshToken: String?,
        @SerializedName("token_type")
        val tokenType: String?
    )
}