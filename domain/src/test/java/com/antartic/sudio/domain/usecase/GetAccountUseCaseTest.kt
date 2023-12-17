package com.antartic.sudio.domain.usecase

import com.antartic.sudio.data.model.account.Account
import com.antartic.sudio.data.model.operation.Operation
import com.antartic.sudio.data.repository.BankRepositoryImpl
import com.antartic.sudio.domain.usecases.account.GetAccountUseCase
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
class GetAccountUseCaseTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var bankRepository: BankRepositoryImpl

    @InjectMockKs
    lateinit var getAccountUseCase: GetAccountUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun getAccountUseCase_success_test() = runBlocking {
        val mock = mockk<Account>(relaxed = true)
        coEvery { bankRepository.getBankAccount("bankName", "accountId") } returns flowOf(mock)
        getAccountUseCase("bankName", "accountId").collect {
            coVerify { bankRepository.getBankAccount("bankName", "accountId") }
            assertThat(it).isNotNull
            assertThat(it!!.id).isEqualTo(mock.id)
            assertThat(it.balance).isEqualTo("${mock.balance} €")
            assertThat(it.label).isEqualTo(mock.label)
        }
    }

    @Test
    fun getAccountUseCase_error_test() = runBlocking {
        coEvery { bankRepository.getBankAccount("bankName", "accountId") } returns flowOf(null)
        getAccountUseCase("bankName", "accountId").collect {
            coVerify { bankRepository.getBankAccount("bankName", "accountId") }
            assertThat(it).isNull()
        }
    }

    @Test
    fun getBanksUseCase_convert_operation_test() {
        runBlocking {
            val mock = mockk<Operation>(relaxed = true)
            every { mock.date } returns "17/02/2023"
            val result = getAccountUseCase.convert(mock)

            assertThat(result.id).isEqualTo(mock.id)
            assertThat(result.amount).isEqualTo("${mock.amount} €")
            assertThat(result.date).isEqualTo(mock.date)
        }
    }

    @Test
    fun getBanksUseCase_convert_account_test() {
        runBlocking {
            val mock = mockk<Account>(relaxed = true)
            val account1 = mockk<Operation>(relaxed = true)
            every { account1.date } returns "1701385200"
            every { account1.title } returns "aab"
            val account2 = mockk<Operation>(relaxed = true)
            every { account2.date } returns "1701305200"
            val account3 = mockk<Operation>(relaxed = true)
            every { account3.date } returns "1701235200"
            val account4 = mockk<Operation>(relaxed = true)
            every { account4.date } returns "1701385200"
            every { account4.title } returns "bab"
            every { mock.operations } returns listOf(account3, account4, account2, account1)
            val result = getAccountUseCase.convert(mock)

            assertThat(result.id).isEqualTo(mock.id)
            assertThat(result.balance).isEqualTo("${mock.balance} €")
            assertThat(result.label).isEqualTo(mock.label)
            assertThat(result.operations).hasSize(mock.operations.size)
            assertThat(result.operations[0].date).isEqualTo("01/12/2023")
            assertThat(result.operations[0].title).isEqualTo("aab")
            assertThat(result.operations[1].date).isEqualTo("01/12/2023")
            assertThat(result.operations[1].title).isEqualTo("bab")
            assertThat(result.operations[2].date).isEqualTo("30/11/2023")
            assertThat(result.operations[3].date).isEqualTo("29/11/2023")
        }
    }
}
