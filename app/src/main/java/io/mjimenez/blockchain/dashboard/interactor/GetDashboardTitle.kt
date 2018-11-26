package io.mjimenez.blockchain.dashboard.interactor

import io.mjimenez.arch.UseCase
import io.mjimenez.blockchain.charts.ChartsRepository
import io.mjimenez.blockchain.dashboard.interactor.GetDashboardTitle.Arguments
import io.reactivex.Observable
import java.text.NumberFormat

private typealias Result = String

interface GetDashboardTitle : UseCase<Arguments, Result> {
    object Arguments
}

class GetDashboardTitleImpl(
    private val chartsRepository: ChartsRepository
) : GetDashboardTitle {
    override fun execute(arguments: Arguments): Observable<Result> =
        chartsRepository.loadConfirmedTxPerDay()
            .map {
                val lastValue = it.values.last().y.toInt()
                val formattedLastValue = NumberFormat.getIntegerInstance().format(lastValue)
                "Tx/day: $formattedLastValue"
            }
            .startWith("...")
}