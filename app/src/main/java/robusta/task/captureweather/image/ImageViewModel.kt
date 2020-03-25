package robusta.task.captureweather.image

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import robusta.task.captureweather.base.BaseViewModel
import robusta.task.captureweather.common.Repo
import robusta.task.captureweather.common.utils.FileHelper
import robusta.task.captureweather.common.utils.saveBitmap

class ImageViewModel(
    private val repo: Repo,
    private val fileHelper: FileHelper,
    val reload: Boolean
) :
    BaseViewModel<ViewState, ViewEvent>() {
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
        if (reload)
            postState(ViewState())
        else postState(ViewState(loadingWeather = false))
    }

    override fun postEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.GetWeather -> {
                getWeather(event.lat, event.lng)
            }
            is ViewEvent.SaveImage -> {
                saveBitmapFile(fileHelper, event.bitmap)
            }
        }
    }

    private fun saveBitmapFile(fileHelper: FileHelper, bitmap: Bitmap) = launch {
        postState(viewStateValue().copy(loadingWeather = true))
        postState(
            viewStateValue().copy(
                file = saveBitmap(fileHelper, bitmap),
                loadingWeather = false,
                imageSaved = true
            )
        )
    }
}