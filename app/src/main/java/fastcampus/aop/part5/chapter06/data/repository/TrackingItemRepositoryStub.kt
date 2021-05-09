package fastcampus.aop.part5.chapter06.data.repository

import fastcampus.aop.part5.chapter06.data.entity.*
import kotlin.random.Random
import kotlin.random.nextLong

class TrackingItemRepositoryStub : TrackingItemRepository {

    override suspend fun getTrackingItemInformation(): List<Pair<TrackingItem, TrackingInformation>> =
        (1_000_000..1_000_020)
            .map { it.toString() }
            .map { invoice ->
                val currentTimeMillis = System.currentTimeMillis()
                TrackingItem(invoice, ShippingCompany("1", "택배회사")) to
                    TrackingInformation(
                        invoiceNo = invoice,
                        itemName = if (Random.nextBoolean()) "이름 있음" else null,
                        level = Level.values().random(),
                        lastDetail = TrackingDetail(
                            kind = "상하차",
                            where = "역삼역",
                            time = Random.nextLong(
                                currentTimeMillis - 1000L * 60L * 60L * 24L * 20L..currentTimeMillis
                            )
                        )
                    )
            }
            .sortedWith(
                compareBy(
                    { it.second.level },
                    { -(it.second.lastDetail?.time ?: Long.MAX_VALUE) }
                )
            )
}
