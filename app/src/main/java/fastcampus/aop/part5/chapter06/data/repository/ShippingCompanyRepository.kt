package fastcampus.aop.part5.chapter06.data.repository

import fastcampus.aop.part5.chapter06.data.entity.ShippingCompany
import fastcampus.aop.part5.chapter06.data.entity.TrackingInformation
import fastcampus.aop.part5.chapter06.data.entity.TrackingItem
import retrofit2.Response

interface ShippingCompanyRepository {

    suspend fun getShippingCompanies(): List<ShippingCompany>
}
