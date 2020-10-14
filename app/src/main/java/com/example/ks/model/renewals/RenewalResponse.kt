package com.example.ks.model.renewals


import com.google.gson.annotations.SerializedName


data class RenewalResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: List<RenewalModel?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("valid")
    var valid: Boolean?
) {

    data class RenewalModel(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("ddc")
        var ddc: Any?,
        @SerializedName("ddc_url")
        var ddcUrl: String?,
        @SerializedName("dmv")
        var dmv: Any?,
        @SerializedName("dmv_url")
        var dmvUrl: String?,
        @SerializedName("fullPrice")
        var fullPrice: String?="0",
        @SerializedName("id")
        var id: Int?,
        @SerializedName("monthly")
        var monthly: String?="0",
        @SerializedName("monthly2")
        var monthly2: String?="0",
        @SerializedName("option1")
        var option1: String?="0",
        @SerializedName("option2")
        var option2: String?="0",
        @SerializedName("paid")
        var paid: Any?,
        @SerializedName("price")
        var price: String?="0",
        @SerializedName("status")
        var status: Int?,
        @SerializedName("tlc")
        var tlc: Any?,
        @SerializedName("tlc_url")
        var tlcUrl: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user_id")
        var userId: Int?
    )
}