package com.example.ks.models


import com.google.gson.annotations.SerializedName

data class DashBoardResponse(
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
        @SerializedName("contracts_count")
        val contractsCount: Int?,
        @SerializedName("invoices_count")
        val invoicesCount: Int?,
        @SerializedName("policies")
        val policies: List<Policy?>?,
        @SerializedName("user_documents_count")
        val userDocumentsCount: Int?,
        @SerializedName("user_renewals_count")
        val userRenewalsCount: Int?
    ) {
        data class Policy(
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("policy_from")
            val policyFrom: String?,
            @SerializedName("policy_from_str")
            val policyFromStr: String?,
            @SerializedName("policy_from_time_stamp")
            val policyFromTimeStamp: Long?,
            @SerializedName("policy_number")
            val policyNumber: String?,
            @SerializedName("policy_to")
            val policyTo: String?,
            @SerializedName("policy_to_str")
            val policyToStr: String?,
            @SerializedName("policy_to_time_stamp")
            val policyToTimeStamp: Long?,
            @SerializedName("updated_at")
            val updatedAt: String?,
            @SerializedName("user_id")
            val userId: Int?
        )
    }
}