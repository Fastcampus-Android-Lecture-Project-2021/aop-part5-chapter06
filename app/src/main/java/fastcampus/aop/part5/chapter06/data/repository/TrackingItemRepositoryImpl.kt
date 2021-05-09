package fastcampus.aop.part5.chapter06.data.repository

import fastcampus.aop.part5.chapter06.data.api.SweetTrackerApi
import fastcampus.aop.part5.chapter06.data.db.TrackingItemDao
import fastcampus.aop.part5.chapter06.data.entity.TrackingInformation
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrackingItemRepositoryImpl(
    private val trackerApi: SweetTrackerApi,
    private val trackingItemDao: TrackingItemDao,
    private val dispatcher: CoroutineDispatcher
) : TrackingItemRepository {

    override suspend fun getTrackingItemInformation(): List<Pair<TrackingItem, TrackingInformation>> = withContext(dispatcher) {
        trackingItemDao.getAll()
            .mapNotNull { trackingItem ->
                val relatedTrackingInfo = trackerApi.getTrackingInformation(
                    trackingItem.company.code,
                    trackingItem.invoice
                ).body()

                if (relatedTrackingInfo?.invoiceNo.isNullOrBlank()) {
                    null
                } else {
                    trackingItem to relatedTrackingInfo!!
                }
            }
            .sortedWith(
                compareBy(
                    { it.second.level },
                    { -(it.second.lastDetail?.time ?: Long.MAX_VALUE) }
                )
            )
    }
}
