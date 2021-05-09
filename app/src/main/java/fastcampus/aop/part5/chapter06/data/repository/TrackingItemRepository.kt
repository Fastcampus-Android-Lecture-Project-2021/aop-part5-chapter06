package fastcampus.aop.part5.chapter06.data.repository

import fastcampus.aop.part5.chapter06.data.entity.TrackingInformation
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem

interface TrackingItemRepository {

    suspend fun getTrackingItemInformation(): List<Pair<TrackingItem, TrackingInformation>>
}
