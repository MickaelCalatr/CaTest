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

            val result = getAccountUseCase.convert(mock)

            assertThat(result.id).isEqualTo(mock.id)
            assertThat(result.balance).isEqualTo("${mock.balance} €")
            assertThat(result.label).isEqualTo(mock.label)
            assertThat(result.operations).hasSize(mock.operations.size)
        }
    }
}
