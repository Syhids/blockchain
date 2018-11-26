package io.mjimenez.blockchain.dashboard

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.mjimenez.arch.di.ViewModelFactory
import io.mjimenez.arch.di.ViewModelKey
import io.mjimenez.blockchain.charts.ChartsRepository
import io.mjimenez.blockchain.dashboard.interactor.GetDashboardTitle
import io.mjimenez.blockchain.dashboard.interactor.GetDashboardTitleImpl
import io.mjimenez.blockchain.dashboard.interactor.GetMainChart
import io.mjimenez.blockchain.dashboard.interactor.GetMainChartImpl
import io.mjimenez.blockchain.log.Logger

@Module(
    includes = [
        DashboardViewModelsModule::class
    ]
)
class DashboardModule {
    @Provides
    fun provideGetMainChart(chartsRepository: ChartsRepository): GetMainChart {
        return GetMainChartImpl(chartsRepository)
    }

    @Provides
    fun provideGetDashboardTitle(chartsRepository: ChartsRepository): GetDashboardTitle {
        return GetDashboardTitleImpl(chartsRepository)
    }

    @Provides
    fun provideChartViewModel(
        getDashboardTitle: GetDashboardTitle,
        getMainChart: GetMainChart,
        logger: Logger
    ): ChartViewModel {
        return ChartViewModel(
            logger.withTag("ChartViewModel"),
            getDashboardTitle,
            getMainChart
        )
    }
}

@Module
interface DashboardViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    fun chartViewModel(viewModel: ChartViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}