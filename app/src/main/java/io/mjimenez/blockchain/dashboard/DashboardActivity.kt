package io.mjimenez.blockchain.dashboard

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.descriptionOf
import io.mjimenez.arch.BlockchainActivity
import io.mjimenez.arch.load
import io.mjimenez.blockchain.R
import io.mjimenez.blockchain.dashboard.model.ChartUi
import io.mjimenez.blockchain.dashboard.model.SnackbarUi
import io.mjimenez.exhaustive
import kotlinx.android.synthetic.main.main_activity.*

class DashboardActivity : BlockchainActivity() {
    private lateinit var viewModel: ChartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = viewModelOf<ChartViewModel>().load {
            chart.observe(onChartUpdated)
            title.observe(onTitleUpdated)
            snackbar.observe(onSnackbarUpdated)
        }
    }

    private val onTitleUpdated = { title: String ->
        textViewTitle.text = title
    }

    private val onSnackbarUpdated = { snackbar: SnackbarUi ->
        Snackbar.make(main, snackbar.text, Snackbar.LENGTH_LONG)
            .setAction(snackbar.actionText) { viewModel.onSnackbarActionPressed() }
            .show()
    }

    private val onChartUpdated = { chartUi: ChartUi ->
        when (chartUi) {
            ChartUi.Loading -> {
                progressBar.visibility = View.VISIBLE
                chart.visibility = View.GONE
            }
            is ChartUi.Ok -> {
                progressBar.visibility = View.GONE
                chart.visibility = View.VISIBLE

                val dataSet = LineDataSet(chartUi.entries.map { Entry(it.x, it.y) }, chartUi.unit)
                chart.data = LineData(listOf(dataSet))
                chart.description = descriptionOf {
                    text = chartUi.description
                }

                chart.notifyDataSetChanged()
            }
        }.exhaustive
    }
}