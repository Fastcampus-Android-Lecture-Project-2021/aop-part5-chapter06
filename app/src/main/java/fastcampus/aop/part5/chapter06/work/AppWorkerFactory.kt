package fastcampus.aop.part5.chapter06.work

import androidx.work.DelegatingWorkerFactory
import fastcampus.aop.part5.chapter06.data.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineDispatcher

class AppWorkerFactory(
    trackingItemRepository: TrackingItemRepository,
    dispatcher: CoroutineDispatcher
) : DelegatingWorkerFactory() {

    init {
        addFactory(TrackingCheckWorkerFactory(trackingItemRepository, dispatcher))
    }
}
