package robusta.task.captureweather.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import robusta.task.captureweather.common.utils.FileHelper

class HistoryViewModel(private val fileHelper: FileHelper) : ViewModel() {
    private val _paths = MutableLiveData<List<ImagePath>>()
    val paths: LiveData<List<ImagePath>> get() = _paths

    init {
        getFiles()
    }

    private fun getFiles() {
        _paths.value = fileHelper.getDirectoryFiles().map {
            ImagePath(it.path)
        }
    }
}