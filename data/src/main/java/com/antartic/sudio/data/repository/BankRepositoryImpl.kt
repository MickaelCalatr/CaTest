package com.antartic.sudio.data.repository

import com.antartic.sudio.core.IoDispatcher
import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.Bank
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(
    private val bankDataSource: BankRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BankRepository {

    override fun getBanks(): Flow<List<Bank>> = flow {
        emit(bankDataSource.getBanks())
    }.flowOn(dispatcher)

    override fun getBankAccount(bankName: String, accountId: String): Flow<Account?> {
        return getBanks().map { banks ->
            banks.find { it.name == bankName }
        }.map { bank ->
            bank?.accounts?.find { it.id == accountId }
        }.flowOn(dispatcher)
    }
}
