package robusta.task.captureweather.weather.data

import retrofit2.http.GET
import retrofit2.http.Query
import robusta.task.captureweather.weather.models.WeatherResponse

interface WeatherService {

    @GET(WEATHER_PATH)
    suspend fun getWeather(
        @Query(LAT_QUERY) lat: Double,
        @Query(LNG_QUERY) lng: Double,
        @Query(ID_QUERY) appId: String = API_key
    ): WeatherResponse
}