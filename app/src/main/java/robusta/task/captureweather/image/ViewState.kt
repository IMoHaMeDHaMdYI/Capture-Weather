package robusta.task.captureweather.image

import robusta.task.captureweather.weather.models.WeatherResponse
import java.io.File
import java.lang.Exception

data class ViewState(
    val exception: Exception? = null,
    val weather: WeatherResponse? = null,
    val loadingWeather: Boolean = false,
    val file: File? =null
) {
}