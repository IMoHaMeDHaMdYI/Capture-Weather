package robusta.task.captureweather.common

import robusta.task.captureweather.weather.data.WeatherService
import robusta.task.captureweather.weather.models.WeatherResponse

class Repo(private val service: WeatherService) {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return service.getWeather(lat, lon)
    }
}