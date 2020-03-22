package robusta.task.captureweather.weather.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String,
    @Json(name = "description")
    val description: String
) : Parcelable