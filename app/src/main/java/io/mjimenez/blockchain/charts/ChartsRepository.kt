package io.mjimenez.blockchain.charts

import io.mjimenez.blockchain.api.BlockchainApi
import io.mjimenez.blockchain.api.model.ChartsResponse
import io.reactivex.Observable

interface ChartsRepository {
    fun loadConfirmedTxPerDay(): Observable<ChartsResponse>
    fun loadMarketPrice(): Observable<ChartsResponse>
}

class ChartsRepositoryImpl(
    private val api: BlockchainApi
) : ChartsRepository {
    override fun loadConfirmedTxPerDay() = api.getChart(
        chartName = "n-transactions",
        timespan = "1week"
    )

    override fun loadMarketPrice() = api.getChart(
        chartName = "market-price",
        timespan = "8weeks"
    )
}