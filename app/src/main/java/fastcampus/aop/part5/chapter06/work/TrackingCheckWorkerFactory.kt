package fastcampus.aop.part5.chapter06.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import fastcampus.aop.part5.chapter06.data.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineDispatcher

class TrackingCheckWorkerFactory(
    private val trackingItemRepository: TrackingItemRepository,
    private val dispatcher: CoroutineDispatcher
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = when (workerClassName) {
        TrackingCheckWorker::class.java.name -> {
            TrackingCheckWorker(
                appContext,
                workerParameters,
                trackingItemRepository,
                dispatcher
            )
        }
        else -> {
            null
        }
    }
}
