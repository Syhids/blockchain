package io.mjimenez.blockchain.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import io.mjimenez.FunctionalTest
import io.mjimenez.blockchain.TestBlockchainModule
import io.mjimenez.blockchain.TestInjector
import io.mjimenez.blockchain.api.BlockchainApi
import io.mjimenez.blockchain.api.model.ChartsResponse
import io.mjimenez.blockchain.api.model.ChartsResponse.ChartValue
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeoutException

/**
 * TODO: Use MockWebServer to achieve true E2E tests, instead of mocking Retrofit interfaces
 *
 * Remember to run tests with animations disabled!
 *
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DashboardActivityTest : FunctionalTest(DashboardActivity::class) {
    @Mock lateinit var blockchainApi: BlockchainApi

    private val anyChartsResponse = ChartsResponse(
        status = "anyStatus",
        values = listOf(),
        description = "anyDescription",
        name = "anyName",
        period = "anyPeriod",
        unit = "anyUnit"
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        TestInjector(TestBlockchainModule(blockchainApi)).inject()

        givenGettingChartFromApiReturns(
            "n-transactions",
            anyChartsResponse
        )
        givenGettingChartFromApiReturns(
            "market-price",
            anyChartsResponse
        )
    }

    @Test
    fun dashboardFlow() {
        // Title is built with this call
        givenGettingChartFromApiReturns(
            "n-transactions",
            anyChartsResponse.copy(
                values = listOf(ChartValue(0, 100.0))
            )
        )

        startActivity()

        onDashboardScreen {
            assertTitleIs("Tx/day: 100")
            assertChartIsShown(true)
        }
    }

    @Test
    fun connectivityError_inTitle_showsSnackbar_thenRetryingShowsCorrectTitle() {
        // This calls initially fails but after retrying succeeds
        wheneverGetChartIsCalled("n-transactions")
            .thenReturn(
                Observable.error(TimeoutException()),
                Observable.just(
                    anyChartsResponse.copy(
                        values = listOf(ChartValue(0, 100.0))
                    )
                )
            )

        startActivity()

        onDashboardScreen {
            assertTitleIs("...")
            assertSnackbarHasText("Network error")

            clickSnackbarRetry()

            assertTitleIs("Tx/day: 100")
        }
    }

    @Test
    fun connectivityErrorInChart_showsSnackbar_thenRetryingShowsTheChart() {
        // This calls initially fails but after retrying succeeds
        wheneverGetChartIsCalled("market-price")
            .thenReturn(
                Observable.error(TimeoutException()),
                Observable.just(anyChartsResponse)
            )

        startActivity()

        onDashboardScreen {
            assertSnackbarHasText("Network error")
            assertChartIsShown(false)

            clickSnackbarRetry()

            assertChartIsShown(true)
        }
    }

    private fun givenGettingChartFromApiReturns(chartName: String, response: ChartsResponse) {
        wheneverGetChartIsCalled(chartName)
            .thenReturn(Observable.just(response))
    }

    private fun wheneverGetChartIsCalled(chartName: String) = whenever(
        blockchainApi.getChart(
            chartName = eq(chartName),
            timespan = anyOrNull(),
            rollingAverage = anyOrNull(),
            sampled = anyOrNull(),
            start = anyOrNull()
        )
    )
}