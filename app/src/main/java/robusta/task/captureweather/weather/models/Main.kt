package robusta.task.captureweather.weather.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(
    @Json(name = "temp")
    val temp: Float,
    @Json(name = "feels_like")
    val feelsLike: Float,
    @Json(name = "temp_min")
    val min: Float,
    @Json(name = "temp_max")
    val max: Float
) : Parcelable