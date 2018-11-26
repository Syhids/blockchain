package io.mjimenez.blockchain

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.mjimenez.blockchain.di.DaggerBlockchainComponent

class BlockchainApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBlockchainComponent.builder().application(this).build()
    }
}