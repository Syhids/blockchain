package io.mjimenez.blockchain.dashboard.interactor

import com.nhaarman.mockitokotlin2.given
import io.mjimenez.UnitTest
import io.mjimenez.blockchain.api.model.ChartsResponse
import io.mjimenez.blockchain.api.model.ChartsResponse.ChartValue
import io.mjimenez.blockchain.charts.ChartsRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetDashboardTitleImplTest : UnitTest() {
    @Mock private lateinit var chartsRepository: ChartsRepository
    private lateinit var getDashboardTitleImpl: GetDashboardTitleImpl

    private val anyChartsResponse = ChartsResponse(
        "any",
        "any",
        "any",
        "any",
        "any",
        emptyList()
    )

    private val anyArguments = GetDashboardTitle.Arguments

    @Before
    fun setUp() {
        getDashboardTitleImpl = GetDashboardTitleImpl(chartsRepository)
    }

    @Test
    fun firstValueShowsLoading() {
        givenApiReturns(anyChartsResponse)

        val result = getDashboardTitleImpl.execute(anyArguments)

        result.test()
            .assertValueAt(0, "...")
    }

    @Test
    fun titleShowsTxOfLastDay() {
        givenApiReturns(
            aChartsResponseWithValues(
                ChartValue(0L, 500.0),
                ChartValue(1L, 1065.0)
            )
        )

        val result = getDashboardTitleImpl.execute(anyArguments)

        result.test()
            .assertValueAt(1, "Tx/day: 1,065")
    }

    private fun givenApiReturns(response: ChartsResponse) {
        given(chartsRepository.loadConfirmedTxPerDay())
            .willReturn(Observable.just(response))
    }

    private fun aChartsResponseWithValues(vararg values: ChartValue) =
        anyChartsResponse.copy(values = values.toList())
}