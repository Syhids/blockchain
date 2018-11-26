package io.mjimenez.blockchain.api

import io.mjimenez.blockchain.api.model.ChartsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlockchainApi {
    @GET("charts/{chartName}")
    fun getChart(
        @Path("chartName") chartName: String = "transactions-per-second",
        @Query("timespan") timespan: String? = null,
        @Query("rollingAverage") rollingAverage: String? = null,
        @Query("start") start: String? = null,
        @Query("sampled") sampled: Boolean? = null
    ): Observable<ChartsResponse>
}