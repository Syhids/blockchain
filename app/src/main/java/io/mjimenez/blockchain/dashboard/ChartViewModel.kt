package io.mjimenez.blockchain.dashboard

import android.arch.lifecycle.MutableLiveData
import io.mjimenez.arch.BlockchainViewModel
import io.mjimenez.blockchain.dashboard.interactor.GetDashboardTitle
import io.mjimenez.blockchain.dashboard.interactor.GetMainChart
import io.mjimenez.blockchain.dashboard.model.ChartUi
import io.mjimenez.blockchain.dashboard.model.SnackbarUi
import io.mjimenez.blockchain.log.Logger

class ChartViewModel(
    private val logger: Logger,
    private val getDashboardTitle: GetDashboardTitle,
    private val getMainChart: GetMainChart
) : BlockchainViewModel() {
    val chart = MutableLiveData<ChartUi>()
    val title = MutableLiveData<String>()
    val snackbar = MutableLiveData<SnackbarUi>()

    override fun onLoad() {
        logger.d("Loading")
        loadChart()
        refreshTitle()
    }

    private fun refreshTitle() {
        getDashboardTitle.execute(GetDashboardTitle.Arguments)
            .postValuesTo(title, onError = ::onError)
    }

    private fun loadChart() {
        getMainChart.execute(GetMainChart.Arguments)
            .postValuesTo(chart, onError = ::onError)
    }

    private fun onError(throwable: Throwable) {
        logger.w("Error happened", throwable)

        snackbar.postValue(
            SnackbarUi(
                text = "Network error",
                actionText = "RETRY"
            )
        )
    }

    fun onSnackbarActionPressed() {
        // There is only one action for now. In the future, use an enum maybe
        onRetryPressedWhenNetworkError()
    }

    private fun onRetryPressedWhenNetworkError() {
        clearDisposables()
        onLoad()
    }
}