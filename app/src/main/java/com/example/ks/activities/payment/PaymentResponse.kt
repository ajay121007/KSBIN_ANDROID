package com.example.ks.activities.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<PaymentModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("valid")
    val valid: Boolean
) {
    data class PaymentModel(
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
        val price: Float,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("updated_at_str")
        val updatedAtStr: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("due_date_str")
    val dueDateStr: String
    )
}