package com.antartic.sudio.catest.features.banks

import app.cash.turbine.test
import com.antartic.sudio.domain.usecases.banks.GetBanksUseCase
import io.mockk.*
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
class BanksViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getBanksUseCase: GetBanksUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun initPage_success_test() = runTest {
        val mock = mockk<GetBanksUseCase.BanksList>(relaxed = true)
        coEvery { getBanksUseCase() } returns flowOf(mock)
        val viewModel = BanksViewModel(getBanksUseCase, testDispatcher)

        viewModel.uiState.test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(BanksUiState.Result::class.java)
            coVerify { getBanksUseCase() }
        }
    }

    @Test
    fun initPage_error_test() = runTest {
        coEvery { getBanksUseCase() } returns flow { throw Exception() }
        val viewModel = BanksViewModel(getBanksUseCase, testDispatcher)

        viewModel.uiState.test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(BanksUiState.Error::class.java)
            coVerify { getBanksUseCase() }
        }
    }
}
