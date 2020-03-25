package robusta.task.captureweather.image

sealed class ViewEvent {
    data class GetWeather(val lat: Double, val lng: Double) : ViewEvent()
    object SaveImage : ViewEvent()
}