package robusta.task.captureweather.image

sealed class ViewEvent {
    object GetWeather : ViewEvent()
    object SaveImage : ViewEvent()
}