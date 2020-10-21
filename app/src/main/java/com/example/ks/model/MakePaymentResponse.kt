package com.example.ks.model


import com.google.gson.annotations.SerializedName

import androidx.annotation.Keep


data class MakePaymentResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("valid")
    var valid: Boolean?
) {

    data class Data(
        @SerializedName("em")
        var em: String?,
        @SerializedName("fee")
        var fee: String?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("invoice_url")
        var invoiceUrl: String?,
        @SerializedName("mt")
        var mt: String?,
        @SerializedName("nm")
        var nm: String?,
        @SerializedName("sub")
        var sub: Int?,
        @SerializedName("ttl")
        var ttl: String?
    )
}