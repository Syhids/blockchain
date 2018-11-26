package io.mjimenez.blockchain.api.model

data class ChartsResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ChartValue>
) {
    data class ChartValue(
        val x: Long,
        val y: Double
    )
}