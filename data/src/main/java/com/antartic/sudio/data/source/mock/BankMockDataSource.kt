package com.antartic.sudio.data.source.mock

import android.content.Context
import com.antartic.sudio.data.model.bank.Bank
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    private fun Context.openJson(path: String): String =
        assets.open(path).bufferedReader().use { it.readText() }

    override suspend fun getBanks(): List<Bank> = withContext(Dispatchers.IO) {
        val type = object : TypeToken<List<Bank>>() {}.type
        gson.fromJson(context.openJson(BANKS_FILE), type)
    }
}
