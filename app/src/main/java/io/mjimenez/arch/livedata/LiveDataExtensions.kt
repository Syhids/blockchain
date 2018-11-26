package io.mjimenez.arch.livedata

import android.arch.lifecycle.Observer

/**
 * Non-null Android lyfecycle's Observer
 * @see Observer
 */
fun <T : Any> observerOf(observer: (T) -> Unit) = Observer<T> {
    observer(it!!) // The generic is already restricting to non-nulls
}

fun <T : Any> nullableObserverOf(observer: (T?) -> Unit) = Observer<T> {
    observer(it)
}