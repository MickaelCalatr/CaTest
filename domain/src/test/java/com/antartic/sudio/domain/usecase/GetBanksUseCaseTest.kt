package com.antartic.sudio.domain.usecase

import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.Bank
import com.antartic.sudio.data.repository.BankRepositoryImpl
import com.antartic.sudio.domain.usecases.banks.GetBanksUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBanksUseCaseTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var bankRepository: BankRepositoryImpl

    @InjectMockKs
    lateinit var getBanksUseCase: GetBanksUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun getBanksUseCase_test() = runBlocking {
        val caBankMock = mockk<Bank>(relaxed = true)
        every { caBankMock.isCA } returns 1
        val otherBankMock = mockk<Bank>(relaxed = true)
        every { otherBankMock.isCA } returns 0

        coEvery { bankRepository.getBanks() } returns flowOf(listOf(caBankMock, otherBankMock, caBankMock))
        getBanksUseCase().collect {
            coVerify { bankRepository.getBanks() }
            assertThat(it.caBanks).hasSize(2)
            assertThat(it.otherBanks).hasSize(1)
        }
    }

    @Test
    fun getBanksUseCase_convert_bank_test() {
        runBlocking {
            val mock = mockk<Bank>(relaxed = true)
            val account1 = mockk<Account>(relaxed = true)
            every { account1.label } returns "aab"
            val account2 = mockk<Account>(relaxed = true)
            every { account2.label } returns "cab"
            val account3 = mockk<Account>(relaxed = true)
            every { account3.label } returns "bab"
            every { mock.isCA } returns 1
            every { mock.accounts } returns listOf(account3, account2, account1)
            val result = getBanksUseCase.convert(mock)

            assertThat(result.name).isEqualTo(mock.name)
            assertThat(result.isCA).isTrue
            assertThat(result.accounts).hasSize(mock.accounts.size)
            assertThat(result.accounts[0].label).isEqualTo("aab")
            assertThat(result.accounts[1].label).isEqualTo("bab")
            assertThat(result.accounts[2].label).isEqualTo("cab")

        }
    }

    @Test
    fun getBanksUseCase_convert_account_test() {
        runBlocking {
            val mock = mockk<Account>(relaxed = true)

            val result = getBanksUseCase.convert(mock)

            assertThat(result.id).isEqualTo(mock.id)
            assertThat(result.balance).isEqualTo("${mock.balance} €")
            assertThat(result.label).isEqualTo(mock.label)
        }
    }
}
