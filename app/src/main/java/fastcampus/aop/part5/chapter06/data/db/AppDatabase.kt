package fastcampus.aop.part5.chapter06.data.db

import android.content.Context
import androidx.room.*
import fastcampus.aop.part5.chapter06.data.entity.ShippingCompany
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem

@Database(
    entities = [TrackingItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackingItemDao(): TrackingItemDao

    companion object {

        private const val DATABASE_NAME = "tracking.db"

        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
