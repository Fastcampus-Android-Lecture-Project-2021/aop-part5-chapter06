package fastcampus.aop.part5.chapter06.data.api

import fastcampus.aop.part5.chapter06.BuildConfig
import fastcampus.aop.part5.chapter06.data.entity.TrackingInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SweetTrackerApi {

    @GET("api/v1/trackingInfo?t_key=${BuildConfig.SWEET_TRACKER_API_KEY}")
    suspend fun getTrackingInformation(
        @Query("t_code") companyCode: String,
        @Query("t_invoice") invoice: String
    ): Response<TrackingInformation>
}
