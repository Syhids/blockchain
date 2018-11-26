package io.mjimenez.blockchain.log

import android.util.Log

/**
 * Or just use Timber :)
 */

interface Logger {
    fun d(message: String)
    fun i(message: String)
    fun w(message: String, throwable: Throwable? = null)
    fun e(message: String, throwable: Throwable? = null)

    fun withTag(tag: String): Logger
}

data class VeryBasicLogger(private val tag: String = "") : Logger {
    override fun d(message: String) {
        Log.d(tag, message)
    }

    override fun i(message: String) {
        Log.i(tag, message)
    }

    override fun w(message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun e(message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun withTag(tag: String): Logger {
        return copy(tag = tag)
    }
}