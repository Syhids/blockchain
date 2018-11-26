package io.mjimenez.blockchain

import androidx.test.platform.app.InstrumentationRegistry

class TestInjector(private val testBlockchainModule: TestBlockchainModule) {

    fun inject() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as BlockchainApplication

        DaggerTestBlockchainComponent
            .builder()
            .blockchainModule(testBlockchainModule)
            .application(app)
            .build()
            .inject(app)
    }
}