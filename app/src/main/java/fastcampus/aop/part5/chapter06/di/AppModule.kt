package fastcampus.aop.part5.chapter06.di

import android.app.Activity
import fastcampus.aop.part5.chapter06.BuildConfig
import fastcampus.aop.part5.chapter06.data.api.SweetTrackerApi
import fastcampus.aop.part5.chapter06.data.api.Url
import fastcampus.aop.part5.chapter06.data.db.AppDatabase
import fastcampus.aop.part5.chapter06.data.entity.TrackingInformation
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem
import fastcampus.aop.part5.chapter06.data.preference.PreferenceManager
import fastcampus.aop.part5.chapter06.data.preference.SharedPreferenceManager
import fastcampus.aop.part5.chapter06.data.repository.*
import fastcampus.aop.part5.chapter06.presentation.addtrackingitem.AddTrackingItemFragment
import fastcampus.aop.part5.chapter06.presentation.addtrackingitem.AddTrackingItemPresenter
import fastcampus.aop.part5.chapter06.presentation.addtrackingitem.AddTrackingItemsContract
import fastcampus.aop.part5.chapter06.presentation.trackinghistory.TrackingHistoryContract
import fastcampus.aop.part5.chapter06.presentation.trackinghistory.TrackingHistoryFragment
import fastcampus.aop.part5.chapter06.presentation.trackinghistory.TrackingHistoryPresenter
import fastcampus.aop.part5.chapter06.presentation.trackingitems.TrackingItemsContract
import fastcampus.aop.part5.chapter06.presentation.trackingitems.TrackingItemsFragment
import fastcampus.aop.part5.chapter06.presentation.trackingitems.TrackingItemsPresenter
import fastcampus.aop.part5.chapter06.work.AppWorkerFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao() }
    single { get<AppDatabase>().shippingCompanyDao() }

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

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }

    // Repository
//    single<TrackingItemRepository> { TrackingItemRepositoryStub() }
    single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(), get(), get()) }
    single<ShippingCompanyRepository> { ShippingCompanyRepositoryImpl(get(), get(), get(), get()) }

    // Work
    single { AppWorkerFactory(get(), get()) }

    // Presentation
    scope<TrackingItemsFragment> {
        scoped<TrackingItemsContract.Presenter> { TrackingItemsPresenter(getSource(), get()) }
    }
    scope<AddTrackingItemFragment> {
        scoped<AddTrackingItemsContract.Presenter> {
            AddTrackingItemPresenter(getSource(), get(), get())
        }
    }
    scope<TrackingHistoryFragment> {
        scoped<TrackingHistoryContract.Presenter> { (trackingItem: TrackingItem, trackingInformation: TrackingInformation) ->
            TrackingHistoryPresenter(getSource(), get(), trackingItem, trackingInformation)
        }
    }
}
