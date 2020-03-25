package robusta.task.captureweather.image

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import robusta.task.captureweather.base.BaseViewModel
import robusta.task.captureweather.common.Repo

class ImageViewModel(private val repo: Repo) : BaseViewModel<ViewState, ViewEvent>() {
    private fun getWeather(lat: Double, lon: Double) = launch {
        postState(viewStateValue().copy(loadingWeather = true))
        // This is supposed to be in a use case to handle the mapping and return a result
        // that maybe success of failure, but enough over engineering for this project :(
        try {
            postState(
                viewStateValue().copy(
                    weather = repo.getWeather(lat, lon),
                    loadingWeather = false
                )
            )
        } catch (e: Exception) {
            postState(viewStateValue().copy(loadingWeather = false, exception = e))
        }
    }

    override fun initViewState() {
        postState(ViewState())
    }

    override fun postEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.GetWeather -> {
                getWeather(event.lat, event.lng)
            }
            ViewEvent.SaveImage -> {

            }
        }
    }
}