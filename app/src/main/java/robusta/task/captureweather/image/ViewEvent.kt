package robusta.task.captureweather.image

import android.graphics.Bitmap

sealed class ViewEvent {
    data class GetWeather(val lat: Double, val lng: Double) : ViewEvent()
    class SaveImage(val bitmap: Bitmap) : ViewEvent()
}