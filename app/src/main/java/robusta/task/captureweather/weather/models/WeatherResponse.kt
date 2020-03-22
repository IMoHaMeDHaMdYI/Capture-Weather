package robusta.task.captureweather.weather.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    @Json(name = "weather")
    val weatherList: List<Weather>,
    @Json(name = "main")
    val main: Main
) : Parcelable