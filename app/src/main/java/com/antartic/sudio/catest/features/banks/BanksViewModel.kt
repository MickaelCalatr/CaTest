package com.antartic.sudio.catest.features.banks

import androidx.lifecycle.viewModelScope
import com.antartic.sudio.catest.core.BaseViewModel
import com.antartic.sudio.catest.utils.Result
import com.antartic.sudio.catest.utils.asResult
import com.antartic.sudio.core.MainDispatcher
import com.antartic.sudio.domain.usecases.banks.GetBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BanksViewModel @Inject constructor(
    getBanksUseCase: GetBanksUseCase,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    val uiState: StateFlow<BanksUiState> = getBanksUseCase()
        .distinctUntilChanged()
        .asResult()
        .map {
            when (it) {
                is Result.Error -> BanksUiState.Error
                Result.Loading -> BanksUiState.Loading
                is Result.Success -> BanksUiState.Result(it.data)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,// WhileSubscribed(5_000),
            initialValue = BanksUiState.Loading,
        )
}

sealed interface BanksUiState {
    object Error : BanksUiState
    object Loading : BanksUiState
    data class Result(val data: GetBanksUseCase.BanksList) : BanksUiState
}

