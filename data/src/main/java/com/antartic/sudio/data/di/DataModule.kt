package com.antartic.sudio.data.di

import android.content.Context
import com.antartic.sudio.core.IoDispatcher
import com.antartic.sudio.data.BuildConfig
import com.antartic.sudio.data.repository.BankRepository
import com.antartic.sudio.data.repository.BankRepositoryImpl
import com.antartic.sudio.data.source.mock.BankMockDataSource
import com.antartic.sudio.data.source.remote.BankRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BANK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(
        @ApplicationContext context: Context,
        retrofit: Retrofit,
        gson: Gson
    ): BankRemoteDataSource {
        return if (BuildConfig.IS_MOCK) {
            BankMockDataSource(context, gson)
        } else {
            retrofit.create(BankRemoteDataSource::class.java)
        }
    }

    @Provides
    fun provideBankRepository(
        bankDataSource: BankRemoteDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): BankRepository {
        return BankRepositoryImpl(bankDataSource, dispatcher)
    }
}
