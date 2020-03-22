package robusta.task.captureweather.camera.views

sealed class CameraViewEvent {
    object Capture : CameraViewEvent()
    object Save : CameraViewEvent()
    object Cancel : CameraViewEvent()
    object StartCamera : CameraViewEvent()
}