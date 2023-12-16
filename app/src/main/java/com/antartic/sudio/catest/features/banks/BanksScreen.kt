package com.antartic.sudio.catest.features.banks

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antartic.sudio.catest.R
import com.antartic.sudio.domain.usecases.banks.GetBanksUseCase
import com.antartic.sudio.ui_ds.atoms.loader.CALoader
import com.antartic.sudio.ui_ds.atoms.text.CABankTitleText
import com.antartic.sudio.ui_ds.molecules.account.CaAccountTitle
import com.antartic.sudio.ui_ds.molecules.bank.CaBankTitle
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.bold22
import com.antartic.sudio.ui_ds.theme.bold40
import com.antartic.sudio.ui_ds.theme.margin16
import com.antartic.sudio.ui_ds.theme.margin28


@Composable
fun BanksRoute(
    viewModel: BanksViewModel = hiltViewModel(),
    navigateToAccount: (String, String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    BanksScreen(
        uiState.value,
        navigateToAccount = navigateToAccount,
    )
}

@Composable
fun BanksScreen(
    uiState: BanksUiState,
    navigateToAccount: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            modifier = Modifier.padding(
                start = margin16(),
                top = margin28()
            ),
            text = stringResource(R.string.my_accounts),
            color = MaterialTheme.colorScheme.onBackground,
            style = bold40()
        )
        when (uiState) {
            BanksUiState.Loading -> CALoader(modifier = Modifier.fillMaxSize())
            BanksUiState.Error -> Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(margin28()),
                text = stringResource(R.string.error_occurred),
                color = MaterialTheme.colorScheme.onBackground,
                style = bold22()
            )
            is BanksUiState.Result -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (!uiState.data.caBanks.isNullOrEmpty()) {
                    item {
                        CABankTitleText(text = stringResource(R.string.ca_bank_title))
                    }
                    items(
                        items = uiState.data.caBanks!!,
                        key = { it.name + it.accounts }
                    ) {
                        BankSection(it)
                    }
                }
                if (!uiState.data.otherBanks.isNullOrEmpty()) {
                    item {
                        CABankTitleText(text = stringResource(R.string.other_bank_title))
                    }
                    items(
                        items = uiState.data.otherBanks!!,
                        key = { it.name + it.accounts }
                    ) {
                        BankSection(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun BankSection(
    bank: GetBanksUseCase.BankData
) {
    var isCollapse by remember { mutableStateOf(true) }

    CaBankTitle(
        accountName = bank.name,
        balance = bank.balance,
        isCollapsed = isCollapse,
        onClick = {
            isCollapse = !isCollapse
        },
        collapseContent = {
            bank.accounts.forEach {
                CaAccountTitle(
                    accountName = it.label,
                    balance = it.balance,
                    onClick = {

                    }
                )
            }
        }
    )
}

@Preview(name = "Result")
@Preview(name = "Result", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ResultPreview() {
    CATheme {
        Surface {
            BanksScreen(
                uiState = BanksUiState.Result(
                    GetBanksUseCase.BanksList(
                        listOf(
                            GetBanksUseCase.BankData(
                                "Credit Agricole",
                                true,
                                "300 €",
                                emptyList()
                            )
                        ),
                        listOf(GetBanksUseCase.BankData("Bourso", false, "300 €", emptyList()))
                    )
                ),
                navigateToAccount = { _, _ -> }
            )
        }
    }
}
