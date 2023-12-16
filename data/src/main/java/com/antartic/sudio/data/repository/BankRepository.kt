package com.antartic.sudio.data.repository

import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.Bank
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getBanks(): Flow<List<Bank>>
    fun getBankAccount(bankName: String, accountId: String): Flow<Account?>
}
