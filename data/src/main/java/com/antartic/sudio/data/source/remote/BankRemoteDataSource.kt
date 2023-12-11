package com.antartic.sudio.data.source.remote

import com.antartic.sudio.data.model.bank.BankList
import retrofit2.http.GET

interface BankRemoteDataSource {
    @GET("bank.json")
    suspend fun getBanks(): BankList
}
