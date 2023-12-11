package com.antartic.sudio.data.model.bank

import com.google.gson.annotations.SerializedName
import com.antartic.sudio.data.model.account.Account

data class Bank(
    @SerializedName("name")
    val name: String,
    @SerializedName("isCA")
    val isCA: Int,
    @SerializedName("accounts")
    val accounts: List<Account>
)
