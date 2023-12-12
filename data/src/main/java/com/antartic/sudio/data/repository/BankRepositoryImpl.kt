package com.antartic.sudio.data.repository

import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.BankList
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(
    private val bankDataSource: BankRemoteDataSource
) : BankRepository {
    override fun getBanks(): Flow<BankList> = flow {
        emit(bankDataSource.getBanks())
    }

    override fun getBankAccount(bankName: String, accountId: String): Flow<Account?> {
        return getBanks().map { banks ->
            banks.banks.find { it.name == bankName }
        }.map { bank ->
            bank?.accounts?.find { it.id == accountId }
        }
    }
}
