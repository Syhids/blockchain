package io.mjimenez.blockchain

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.mjimenez.blockchain.di.ActivityBuilder
import io.mjimenez.blockchain.di.BlockchainModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BlockchainModule::class,
        ActivityBuilder::class
    ]
)
interface TestBlockchainComponent : AndroidInjector<BlockchainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun blockchainModule(blockchainModule: BlockchainModule): Builder

        fun build(): TestBlockchainComponent
    }
}

