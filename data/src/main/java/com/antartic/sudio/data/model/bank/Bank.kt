package com.antartic.sudio.data.model.bank

import com.antartic.sudio.data.model.account.Account
import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("name")
    val name: String,
    @SerializedName("isCA")
    val isCA: Int,
    @SerializedName("accounts")
    val accounts: List<Account>
)
