package io.mjimenez.blockchain.dashboard.interactor

import io.mjimenez.arch.UseCase
import io.mjimenez.blockchain.charts.ChartsRepository
import io.mjimenez.blockchain.dashboard.interactor.GetMainChart.Arguments
import io.mjimenez.blockchain.dashboard.model.ChartUi
import io.reactivex.Observable

interface GetMainChart : UseCase<Arguments, ChartUi> {
    object Arguments
}

class GetMainChartImpl(
    private val chartsRepository: ChartsRepository
) : GetMainChart {
    override fun execute(arguments: Arguments): Observable<ChartUi> =
        chartsRepository.loadMarketPrice()
            .map<ChartUi> { response ->
                val values = response.values.map { ChartUi.Entry(it.x.toFloat(), it.y.toFloat()) }
                ChartUi.Ok(
                    entries = values,
                    description = response.name,
                    unit = response.unit
                )
            }
            .startWith(ChartUi.Loading)
}