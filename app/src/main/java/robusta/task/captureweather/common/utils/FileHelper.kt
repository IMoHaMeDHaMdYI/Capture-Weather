package robusta.task.captureweather.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.FileObserver
import android.util.Log
import robusta.task.captureweather.common.extenstions.TAG
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
        return "$home${UUID.randomUUID()}.$postfix"
    }

    fun newFile(postfix: String): File {
        return File("$home${UUID.randomUUID()}.$postfix").apply {
            if (!exists()) mkdirs()
            createNewFile()
        }
    }

    fun newFile(prefix: String, postfix: String): File {
        return File("$home$prefix.$postfix").apply { createNewFile() }
    }
}