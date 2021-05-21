package fastcampus.aop.part5.chapter06.data.entity


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ShippingCompany(
    @PrimaryKey
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String
) : Parcelable
