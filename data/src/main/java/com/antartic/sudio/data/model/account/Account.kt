package com.antartic.sudio.data.model.account

import com.google.gson.annotations.SerializedName
import com.antartic.sudio.data.model.operation.Operation

data class Account(
    @SerializedName("order")
    val order: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("holder")
    val holder: String,
    @SerializedName("role")
    val role: Int,
    @SerializedName("contact_number")
    val contactNumber: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("product_code")
    val productCode: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("operations")
    val operations: List<Operation>
)
