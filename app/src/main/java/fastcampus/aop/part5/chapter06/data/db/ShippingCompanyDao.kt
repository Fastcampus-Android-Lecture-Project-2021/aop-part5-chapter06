package fastcampus.aop.part5.chapter06.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fastcampus.aop.part5.chapter06.data.entity.ShippingCompany
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem

@Dao
interface ShippingCompanyDao {

    @Query("SELECT * FROM ShippingCompany")
    suspend fun getAll(): List<ShippingCompany>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: List<ShippingCompany>)
}
