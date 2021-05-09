package fastcampus.aop.part5.chapter06.di

import fastcampus.aop.part5.chapter06.BuildConfig
import fastcampus.aop.part5.chapter06.data.api.SweetTrackerApi
import fastcampus.aop.part5.chapter06.data.api.Url
import fastcampus.aop.part5.chapter06.data.db.AppDatabase
import fastcampus.aop.part5.chapter06.data.repository.TrackingItemRepository
import fastcampus.aop.part5.chapter06.data.repository.TrackingItemRepositoryImpl
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao() }

    // Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<SweetTrackerApi> {
        Retrofit.Builder().baseUrl(Url.SWEET_TRACKER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    // Repository
    single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(), get(), get()) }
}
