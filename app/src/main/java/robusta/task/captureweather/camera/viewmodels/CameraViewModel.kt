package robusta.task.captureweather.camera.viewmodels

import org.koin.ext.scope
import robusta.task.captureweather.base.BaseViewModel
import robusta.task.captureweather.camera.views.CameraViewEvent
import robusta.task.captureweather.camera.views.CameraViewState
import robusta.task.captureweather.common.Event

class CameraViewModel : BaseViewModel<CameraViewState, CameraViewEvent>() {

    override fun postEvent(event: CameraViewEvent) {
        when (event) {
            CameraViewEvent.Capture -> {

            }
            CameraViewEvent.Save -> {

            }
            CameraViewEvent.Cancel -> {

            }
            CameraViewEvent.StartCamera -> {
                startCamera()
            }
        }
    }

    private fun startCamera() {
        postState(viewStateValue().copy(permissionGranted = true))
    }

    override fun initViewState() {
        postState(CameraViewState())
    }
}