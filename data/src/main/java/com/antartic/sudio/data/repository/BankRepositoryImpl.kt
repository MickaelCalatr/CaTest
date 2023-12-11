package com.antartic.sudio.data.repository

import com.antartic.sudio.data.model.bank.BankList
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(
    private val bankDataSource: BankRemoteDataSource
): BankRepository {
    override fun getBanks(): Flow<BankList> = flow {
        emit(bankDataSource.getBanks())
    }
}
