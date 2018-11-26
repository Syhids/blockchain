package io.mjimenez.blockchain.dashboard.interactor

import com.nhaarman.mockitokotlin2.given
import io.mjimenez.UnitTest
import io.mjimenez.blockchain.api.model.ChartsResponse
import io.mjimenez.blockchain.api.model.ChartsResponse.ChartValue
import io.mjimenez.blockchain.charts.ChartsRepository
import io.mjimenez.blockchain.dashboard.model.ChartUi
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMainChartImplTest : UnitTest() {
    @Mock private lateinit var chartsRepository: ChartsRepository
    private lateinit var getMainChartImpl: GetMainChartImpl

    private val anyChartsResponse = ChartsResponse(
        "any",
        "any",
        "any",
        "any",
        "any",
        emptyList()
    )

    private val anyArguments = GetMainChart.Arguments

    @Before
    fun setUp() {
        getMainChartImpl = GetMainChartImpl(chartsRepository)
    }

    @Test
    fun firstValueShowsLoading() {
        givenApiReturns(anyChartsResponse)

        val result = getMainChartImpl.execute(anyArguments)

        result.test()
            .assertValueAt(0, ChartUi.Loading)
    }

    @Test
    fun secondValueShouldBeTheMappedChart() {
        givenApiReturns(
            ChartsResponse(
                status = "any",
                name = "Number of transactions",
                unit = "Transactions",
                period = "any",
                description = "any",
                values = listOf(
                    ChartValue(0L, 500.0),
                    ChartValue(1L, 1065.0)
                )
            )
        )

        val result = getMainChartImpl.execute(anyArguments)

        result.test()
            .assertValueAt(
                1, ChartUi.Ok(
                    entries = listOf(
                        ChartUi.Entry(0f, 500f),
                        ChartUi.Entry(1f, 1065f)
                    ),
                    description = "Number of transactions",
                    unit = "Transactions"
                )
            )
    }

    private fun givenApiReturns(response: ChartsResponse) {
        given(chartsRepository.loadMarketPrice())
            .willReturn(Observable.just(response))
    }
}