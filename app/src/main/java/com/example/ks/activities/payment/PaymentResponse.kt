package com.example.ks.activities.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("valid")
    val valid: Boolean
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("due")
        val due: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("invoice")
        val invoice: String,
        @SerializedName("invoice_no")
        val invoiceNo: String,
        @SerializedName("paid")
        val paid: Int,
        @SerializedName("policy")
        val policy: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int
    )
}