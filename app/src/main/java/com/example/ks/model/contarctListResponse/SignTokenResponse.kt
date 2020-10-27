package com.example.ks.model.contarctListResponse


import com.google.gson.annotations.SerializedName

data class SignTokenResponse(
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
        val token: String?
    )
}

data class ChangePasswordResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("valid")
    val valid: Boolean?
) {
    data class Data(
        @SerializedName("token")
        val token: String?
    )
}