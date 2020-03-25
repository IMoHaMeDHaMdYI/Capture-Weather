package robusta.task.captureweather.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.utils.FileHelper
import java.io.File

class HistoryViewModel(private val fileHelper: FileHelper) : ViewModel() {
    val _pathes = MutableLiveData<List<ImagePath>>()
    val pathes: LiveData<List<ImagePath>> get() = _pathes

    init {
        getFiles()
    }

    private fun getFiles() {
        _pathes.value = fileHelper.getDirectoryFiles().map {
            Log.d(TAG,it.path)
            ImagePath(it.path)
        }
    }
}