package com.antartic.sudio.catest.navigation

sealed class NavigationItems(var screenRoute: String) {
    companion object {
        const val BANK_NAME = "bankName"
        const val ACCOUNT_ID = "accountId"
    }
    object BankPage : NavigationItems("bank")
    object AccountPage : NavigationItems("bank/{bankName}/account/{accountId}")
}
