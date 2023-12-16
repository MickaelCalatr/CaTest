package com.antartic.sudio.catest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.antartic.sudio.catest.features.banks.BanksRoute


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItems.BankPage.screenRoute
    ) {
        composable(route = NavigationItems.BankPage.screenRoute) {
            BanksRoute(
                navigateToAccount = { bankName, accountId ->
                    navController.navigate("bank/$bankName/account/$accountId")
                }
            )
        }
        composable(
            route = NavigationItems.AccountPage.screenRoute,
            arguments = listOf(
                navArgument(NavigationItems.BANK_NAME) { type = NavType.StringType },
                navArgument(NavigationItems.ACCOUNT_ID) { type = NavType.StringType }
            )
        ) {

        }
    }
}
