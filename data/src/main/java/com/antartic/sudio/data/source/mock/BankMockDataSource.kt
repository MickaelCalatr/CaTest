package com.antartic.sudio.data.source.mock

import android.content.Context
import com.antartic.sudio.data.model.bank.BankList
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankMockDataSource @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : BankRemoteDataSource {

    companion object {
        private const val BANKS_FILE = "data/banks.json"
    }

    private inline fun <reified T> Gson.fromJson(json: String): T =
        this.fromJson(json, T::class.java)

    private fun Context.openJson(path: String): String =
        assets.open(path).bufferedReader().use { it.readText() }

    override suspend fun getBanks(): BankList = withContext(Dispatchers.IO) {
        gson.fromJson(context.openJson(BANKS_FILE))
    }
}
