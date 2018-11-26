package io.mjimenez.blockchain.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.mjimenez.blockchain.dashboard.DashboardActivity
import io.mjimenez.blockchain.dashboard.DashboardModule

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector(modules = [DashboardModule::class])
    fun bindDashboardActivity(): DashboardActivity
}