package io.mjimenez.arch

import io.mjimenez.arch.rx.RxViewModel

abstract class BlockchainViewModel : RxViewModel() {
    abstract fun onLoad()
}

/**
 * Perform LiveData subscriptions (observe) here
 */
inline fun <T : BlockchainViewModel> T.load(init: T.() -> Unit): T {
    init()
    clearDisposables()
    onLoad() // Should be after init() in order to have correct LiveData subscriptions
    return this
}