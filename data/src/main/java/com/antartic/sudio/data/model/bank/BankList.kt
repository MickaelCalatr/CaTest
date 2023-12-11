package com.antartic.sudio.data.model.bank

import com.google.gson.annotations.SerializedName
import com.antartic.sudio.data.model.account.Account

data class BankList(
    @SerializedName("banks")
    val banks: List<Bank>
)
