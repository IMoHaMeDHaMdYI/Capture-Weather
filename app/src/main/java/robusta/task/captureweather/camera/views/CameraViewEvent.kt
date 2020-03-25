package robusta.task.captureweather.camera.views

import android.graphics.Bitmap

sealed class CameraViewEvent {
    object Capture : CameraViewEvent()
    class Save(val bitmap: Bitmap) : CameraViewEvent()

    object Cancel : CameraViewEvent()
    object StartCamera : CameraViewEvent()
}