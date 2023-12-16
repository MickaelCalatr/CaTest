package com.antartic.sudio.data.source.remote

import com.antartic.sudio.data.model.bank.Bank
import retrofit2.http.GET

interface BankRemoteDataSource {
    @GET("banks.json")
    suspend fun getBanks(): List<Bank>
}
