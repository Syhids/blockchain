package io.mjimenez.blockchain.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.mjimenez.blockchain.api.BlockchainApi
import io.mjimenez.blockchain.charts.ChartsRepository
import io.mjimenez.blockchain.charts.ChartsRepositoryImpl
import io.mjimenez.blockchain.log.Logger
import io.mjimenez.blockchain.log.VeryBasicLogger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named

@Module
open class BlockchainModule {
    @Provides
    @Named("apiUrl")
    fun provideApiUrl(): String {
        return "https://api.blockchain.info"
    }

    @Provides
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    fun provideLogger(): Logger {
        return VeryBasicLogger("Blockchain")
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideRetrofit(gson: Gson, @Named("apiUrl") apiUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @Provides
    open fun provideBlockchainApi(retrofit: Retrofit): BlockchainApi {
        return retrofit.create()
    }

    @Provides
    fun provideChartsRepository(blockchainApi: BlockchainApi): ChartsRepository {
        return ChartsRepositoryImpl(blockchainApi)
    }
}