package com.antartic.sudio.catest.features.account

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antartic.sudio.catest.R
import com.antartic.sudio.domain.usecases.account.GetAccountUseCase
import com.antartic.sudio.ui_ds.atoms.button.CaBackButton
import com.antartic.sudio.ui_ds.atoms.loader.CALoader
import com.antartic.sudio.ui_ds.atoms.text.CaErrorPlaceholder
import com.antartic.sudio.ui_ds.molecules.operation.CaOperationTitle
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.bold40
import com.antartic.sudio.ui_ds.theme.margin16
import com.antartic.sudio.ui_ds.theme.regular22


@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    bankName: String,
    accountId: String,
    backActionText: String,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAccount(bankName, accountId)
    }
    AccountScreen(
        uiState = uiState.value,
        backActionText = backActionText,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    uiState: AccountUiState,
    backActionText: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CaBackButton(
                text = backActionText,
                onClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (uiState) {
                AccountUiState.Loading -> CALoader(modifier = Modifier.fillMaxSize())
                AccountUiState.Error -> CaErrorPlaceholder(stringResource(R.string.error_occurred))
                is AccountUiState.Result -> Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = margin16()),
                        text = uiState.data?.balance.orEmpty(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = bold40()
                    )
                    Text(
                        modifier = Modifier.padding(vertical = margin16()),
                        text = uiState.data?.label.orEmpty(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = regular22()
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        if (!uiState.data?.operations.isNullOrEmpty()) {
                            items(
                                items = uiState.data!!.operations
                            ) {
                                CaOperationTitle(
                                    operationName = it.title,
                                    amount = it.amount,
                                    date = it.date
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Result")
@Preview(name = "Result", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ResultPreview() {
    CATheme {
        Surface {
            AccountScreen(
                uiState = AccountUiState.Result(
                    GetAccountUseCase.AccountData(
                        id = "",
                        label = "Deposit Account",
                        balance = "30000 €",
                        operations = listOf(
                            GetAccountUseCase.OperationData(
                                id = "",
                                title = "Pizza's papa",
                                amount = "-45 €",
                                date = "24/12/2023"
                            )
                        )
                    )
                ),
                backActionText = "My Accounts",
                onBackClick = {}
            )
        }
    }
}
