package com.antartic.sudio.catest.features.account

import app.cash.turbine.test
import com.antartic.sudio.domain.usecases.account.GetAccountUseCase
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Config(sdk = [29])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AccountViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getAccountUseCase: GetAccountUseCase

    @InjectMockKs
    lateinit var viewModel: AccountViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun initPage_success_test() = runTest {
        val mock = mockk<GetAccountUseCase.AccountData>(relaxed = true)
        coEvery { getAccountUseCase(any(), any()) } returns flowOf(mock)

        viewModel.uiState.test {
            var result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Loading::class.java)

            viewModel.getAccount("bankName", "accountId")
            result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Result::class.java)

            coVerify { getAccountUseCase("bankName", "accountId") }
        }
    }

    @Test
    fun initPage_error_test() = runTest {
        coEvery { getAccountUseCase(any(), any()) } returns flow { throw Exception() }

        viewModel.uiState.test {
            var result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Loading::class.java)

            viewModel.getAccount("bankName", "accountId")
            result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Error::class.java)

            coVerify { getAccountUseCase("bankName", "accountId") }
        }
    }

    @Test
    fun initPage_null_error_test() = runTest {
        coEvery { getAccountUseCase(any(), any()) } returns flowOf(null)

        viewModel.uiState.test {
            var result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Loading::class.java)

            viewModel.getAccount("bankName", "accountId")
            result = awaitItem()
            assertThat(result).isInstanceOf(AccountUiState.Error::class.java)

            coVerify { getAccountUseCase("bankName", "accountId") }
        }
    }
}
