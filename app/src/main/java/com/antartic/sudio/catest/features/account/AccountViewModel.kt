package com.antartic.sudio.catest.features.account

import com.antartic.sudio.catest.core.BaseViewModel
import com.antartic.sudio.catest.utils.Result
import com.antartic.sudio.catest.utils.asResult
import com.antartic.sudio.core.MainDispatcher
import com.antartic.sudio.domain.usecases.account.GetAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    var uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()


    fun getAccount(bankName: String, accountId: String) = launch {
        getAccountUseCase(bankName, accountId)
            .distinctUntilChanged()
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> AccountUiState.Error
                    Result.Loading -> AccountUiState.Loading
                    is Result.Success -> AccountUiState.Result(it.data)
                }
            }
            .collect { _uiState.emit(it) }
    }
}

sealed interface AccountUiState {
    object Error : AccountUiState
    object Loading : AccountUiState
    data class Result(val data: GetAccountUseCase.AccountData?) : AccountUiState
}

