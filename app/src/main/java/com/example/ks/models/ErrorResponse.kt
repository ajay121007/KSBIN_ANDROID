package com.example.ks.models


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
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
        @SerializedName("validations")
        val validations: List<Validation?>?
    ) {
        data class Validation(
            @SerializedName("errors")
            val errors: String?,
            @SerializedName("field")
            val `field`: String?
        )
    }
}