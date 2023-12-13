package com.antartic.sudio.domain.usecases.banks

import androidx.annotation.VisibleForTesting
import com.antartic.sudio.core.DefaultDispatcher
import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.Bank
import com.antartic.sudio.data.repository.BankRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBanksUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    data class BankData(
        val name: String,
        val isCA: Boolean,
        val accounts: List<AccountData>
    )

    data class AccountData(
        val id: String,
        val label: String,
        val balance: String,
    )

    operator fun invoke(): Flow<List<BankData>> {
        return bankRepository.getBanks().map { banks ->
            banks.banks.map { bank ->
                convert(bank)
            }
        }.flowOn(dispatcher)
    }

    @VisibleForTesting
    suspend fun convert(data: Bank): BankData = withContext(dispatcher) {
        BankData(
            name = data.name,
            isCA = data.isCA == 1,
            accounts = data.accounts.sortedBy { it.order }.map { convert(it) }
        )
    }

    @VisibleForTesting
    suspend fun convert(data: Account): AccountData = withContext(dispatcher) {
        AccountData(
            id = data.id,
            label = data.label,
            balance = data.balance
        )
    }
}
