package io.mjimenez.blockchain.dashboard.model

sealed class ChartUi {
    object Loading : ChartUi()

    data class Ok(
        val entries: List<Entry>,
        val description: String,
        val unit: String
    ) : ChartUi()

    data class Entry(val x: Float, val y: Float)
}


