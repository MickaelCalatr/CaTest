package com.antartic.sudio.data.model.operation

import com.google.gson.annotations.SerializedName

data class Operation(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("date")
    val date: String
)
