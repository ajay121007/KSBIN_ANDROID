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
        @SerializedName("user")
        val user: User?,
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

        data class User(
            @SerializedName("contracts")
            val contracts: List<Contract?>?,
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
            @SerializedName("invoices")
            val invoices: List<Invoice?>?,
            @SerializedName("manager_name")
            val managerName: Any?,
            @SerializedName("mobile_number")
            val mobileNumber: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("note")
            val note: Any?,
            @SerializedName("policies")
            val policies: List<Policy?>?,
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
            val userImageUrl: String?,
            @SerializedName("user_invoices")
            val userInvoices: List<UserInvoice?>?
        ) {
            data class Contract(
                @SerializedName("contract")
                val contract: String?,
                @SerializedName("contractLink")
                val contractLink: Any?,
                @SerializedName("contract_url")
                val contractUrl: String?,
                @SerializedName("created_at")
                val createdAt: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("signed")
                val signed: Int?,
                @SerializedName("updated_at")
                val updatedAt: String?,
                @SerializedName("user_id")
                val userId: Int?,
                @SerializedName("userSign")
                val userSign: String?,
                @SerializedName("user_sign_url")
                val userSignUrl: String?
            )

            data class Invoice(
                @SerializedName("created_at")
                val createdAt: String?,
                @SerializedName("due")
                val due: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("invoice")
                val invoice: String?,
                @SerializedName("invoice_no")
                val invoiceNo: String?,
                @SerializedName("paid")
                val paid: Int?,
                @SerializedName("policy")
                val policy: String?,
                @SerializedName("price")
                val price: Int?,
                @SerializedName("updated_at")
                val updatedAt: String?,
                @SerializedName("user_id")
                val userId: Int?
            )
            data class UserInvoice(
                @SerializedName("created_at")
                val createdAt: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("invoice")
                val invoice: String?,
                @SerializedName("invoice_url")
                val invoiceUrl: String?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("updated_at")
                val updatedAt: String?,
                @SerializedName("user_id")
                val userId: Int?
            )
        }
    }
}