package io.mjimenez.blockchain.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.mjimenez.blockchain.BlockchainApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BlockchainModule::class,
        ActivityBuilder::class
    ]
)
interface BlockchainComponent : AndroidInjector<BlockchainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): BlockchainComponent
    }
}

