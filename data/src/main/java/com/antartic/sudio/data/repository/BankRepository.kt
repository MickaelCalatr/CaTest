package com.antartic.sudio.data.repository

import com.antartic.sudio.data.model.bank.BankList
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getBanks(): Flow<BankList>
}
