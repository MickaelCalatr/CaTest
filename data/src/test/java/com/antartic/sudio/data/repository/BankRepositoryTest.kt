package com.antartic.sudio.data.repository

import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.bank.Bank
import com.antartic.sudio.data.source.mock.BankMockDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BankRepositoryTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var bankDataSource: BankMockDataSource

    @InjectMockKs
    lateinit var bankRepository: BankRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun getBanks_test() = runBlocking {
        val mock = mockk<Bank>()
        coEvery { bankDataSource.getBanks() } returns listOf(mock)
        bankRepository.getBanks().collect {
            coVerify { bankDataSource.getBanks() }
            assertThat(it).hasSize(1)
            assertThat(it.first()).isEqualTo(mock)
        }
    }

    @Test
    fun getBankAccount_success_test() = runBlocking {
        val bankMock1 = mockk<Bank>(relaxed = true)
        val bankMock2 = mockk<Bank>(relaxed = true)
        every { bankMock2.name } returns "CA"
        val accountMock1 = mockk<Account>(relaxed = true)
        val accountMock2 = mockk<Account>(relaxed = true)
        every { accountMock2.id } returns "12"
        every { bankMock2.accounts } returns listOf(accountMock1, accountMock2)
        coEvery { bankDataSource.getBanks() } returns listOf(bankMock1, bankMock2)

        bankRepository.getBankAccount("CA", "12").collect {
            coVerify { bankDataSource.getBanks() }
            assertThat(it).isEqualTo(accountMock2)
        }
    }

    @Test
    fun getBankAccount_failure_bankName_test() = runBlocking {
        val bankMock1 = mockk<Bank>(relaxed = true)
        val bankMock2 = mockk<Bank>(relaxed = true)
        every { bankMock2.name } returns "CA"
        val accountMock1 = mockk<Account>(relaxed = true)
        val accountMock2 = mockk<Account>(relaxed = true)
        every { accountMock2.id } returns "12"
        every { bankMock2.accounts } returns listOf(accountMock1, accountMock2)
        coEvery { bankDataSource.getBanks() } returns listOf(bankMock1, bankMock2)

        bankRepository.getBankAccount("C", "12").collect {
            coVerify { bankDataSource.getBanks() }
            assertThat(it).isNull()
        }
    }


    @Test
    fun getBankAccount_failure_accountId_test() = runBlocking {
        val bankMock1 = mockk<Bank>(relaxed = true)
        val bankMock2 = mockk<Bank>(relaxed = true)
        every { bankMock2.name } returns "CA"
        val accountMock1 = mockk<Account>(relaxed = true)
        val accountMock2 = mockk<Account>(relaxed = true)
        every { accountMock2.id } returns "12"
        every { bankMock2.accounts } returns listOf(accountMock1, accountMock2)
        coEvery { bankDataSource.getBanks() } returns listOf(bankMock1, bankMock2)

        bankRepository.getBankAccount("CA", "122").collect {
            coVerify { bankDataSource.getBanks() }
            assertThat(it).isNull()
        }
    }
}
