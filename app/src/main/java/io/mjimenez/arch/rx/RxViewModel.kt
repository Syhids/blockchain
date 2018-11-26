package io.mjimenez.arch.rx

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * Implements auto disposing when using RxViewModel#postValuesTo
 */
abstract class RxViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun <T : Any> Observable<T>.postValuesTo(
        liveData: MutableLiveData<T>,
        onError: (Throwable) -> Unit
    ) = subscribe(liveData::postValue, onError)
        .addTo(disposables)

    override fun onCleared() {
        clearDisposables()
    }

    fun clearDisposables() {
        disposables.clear()
    }
}