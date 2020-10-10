package com.example.ks.model.contarctListResponse


import com.google.gson.annotations.SerializedName

data class ContractResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<ContractModel?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("valid")
    val valid: Boolean?
) {
    data class ContractModel(
        @SerializedName("contract")
        val contract: String?,
        @SerializedName("contractLink")
        val contractLink: Any?,
        @SerializedName("contract_url")
        val contractUrl: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("signed")
        val signed: Int?,
        @SerializedName("update_at_str")
        val updateAtStr: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        @SerializedName("user_id")
        val userId: Int?,
        @SerializedName("userSign")
        val userSign: String?,
        @SerializedName("user_sign_url")
        val userSignUrl: String?,
        @SerializedName("invoice_url")
        val invoiceUrl: String?
    )
}