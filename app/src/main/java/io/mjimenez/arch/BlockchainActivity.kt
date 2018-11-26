package io.mjimenez.arch

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BlockchainActivity : AppCompatActivity() {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun <T> LiveData<T>.observe(func: (T) -> Unit) =
        observe(this@BlockchainActivity, Observer<T> { it?.let(func) })

    protected fun <T> LiveData<T>.observe(observer: Observer<T>) =
        observe(this@BlockchainActivity, observer)


    protected inline fun <reified T : ViewModel> FragmentActivity.viewModelOf() =
        ViewModelProviders.of(this, viewModelProviderFactory).get(T::class.java)
}