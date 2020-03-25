package robusta.task.captureweather.common.utils

import android.content.Context
import java.io.File
import java.util.*

class FileHelper(context: Context) {
    private val home = "${context.filesDir}/images/"
    fun getDirectoryFiles(): List<File> {
        return File(home).listFiles { dir, name ->
            File("${dir.path}/$name").isFile
        }?.toList() ?: emptyList()
    }

    fun generateFileName(postfix: String): String {
        val homeFile = File(home)
        if (!homeFile.exists()) {
            homeFile.mkdirs()
        }
        return "$home${UUID.randomUUID().toString().split("-")[0]}.$postfix"
    }
}