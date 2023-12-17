package com.antartic.sudio.domain.usecases.account

import androidx.annotation.VisibleForTesting
import com.antartic.sudio.core.DefaultDispatcher
import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.operation.Operation
import com.antartic.sudio.data.repository.BankRepository
import com.antartic.sudio.domain.utils.getDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    data class AccountData(
        val id: String,
        val label: String,
        val balance: String,
        val operations: List<OperationData>
    )

    data class OperationData(
        val id: String,
        val title: String,
        val amount: String,
        val date: String?
    )

    operator fun invoke(bankName: String, accountId: String): Flow<AccountData?> {
        return bankRepository.getBankAccount(bankName, accountId)
            .map { account ->
                account?.let { convert(it) }
            }.flowOn(dispatcher)
    }

    @VisibleForTesting
    suspend fun convert(data: Operation): OperationData = withContext(dispatcher) {
        OperationData(
            id = data.id,
            title = data.title,
            amount = "${data.amount} €",
            date = getDate(data.date)
        )
    }

    @VisibleForTesting
    suspend fun convert(data: Account): AccountData = withContext(dispatcher) {
        AccountData(
            id = data.id,
            label = data.label,
            balance = "${data.balance} €",
            operations = data.operations
                .sortedWith(compareByDescending<Operation> { it.date }.thenBy { it.title })
                .map { convert(it) }
        )
    }
}
