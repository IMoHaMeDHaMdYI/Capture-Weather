package robusta.task.captureweather.camera.viewmodels

import robusta.task.captureweather.base.BaseViewModel
import robusta.task.captureweather.camera.views.CameraViewEvent
import robusta.task.captureweather.camera.views.CameraViewState

class CameraViewModel : BaseViewModel<CameraViewState, CameraViewEvent>() {

    override fun postEvent(event: CameraViewEvent) {
        when (event) {
            CameraViewEvent.Capture -> {

            }
            is CameraViewEvent.Save -> {
                postState(viewStateValue().copy(bitmap = event.bitmap))
            }
        }
    }

    override fun initViewState() {
        postState(CameraViewState())
    }
}