package io.mjimenez.blockchain

import io.mjimenez.blockchain.api.BlockchainApi
import io.mjimenez.blockchain.di.BlockchainModule
import retrofit2.Retrofit

/**
 * For now, the cleanest way I found to override dependencies @ testing time with Dagger 2
 */
open class TestBlockchainModule(private val blockchainApi: BlockchainApi) : BlockchainModule() {
    override fun provideBlockchainApi(retrofit: Retrofit): BlockchainApi {
        return blockchainApi
    }
}