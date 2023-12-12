package com.antartic.sudio.domain.usecases.banks

import com.antartic.sudio.data.repository.BankRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBanksUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    
) {
    operator fun invoke(): Flow<> {
        return bankRepository.getBanks().map { banks ->

        }.flowOn(dispatcher)
    }

}
