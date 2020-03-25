package robusta.task.captureweather.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

fun getBitmap(path: String): Bitmap {
    val exif = ExifInterface(path)
    val rotation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    val rotationInDegrees: Int = exifToDegrees(rotation)
    val matrix = Matrix()
    if (rotation != 0) {
        matrix.preRotate(rotationInDegrees.toFloat())
    }
    val bitmap = BitmapFactory.decodeFile(path)
    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        matrix,
        true
    )

}


fun exifToDegrees(exifOrientation: Int): Int {
    return when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> {
            90
        }
        ExifInterface.ORIENTATION_ROTATE_180 -> {
            180
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> {
            270
        }
        else -> 0
    }
}

suspend fun saveBitmap(fileHelper: FileHelper, bitmap: Bitmap): File {
    val path = fileHelper.generateFileName("png")
    val op = FileOutputStream(path)
    try {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, op)
        op.flush()
    } catch (e: Exception) {

    } finally {
        op.close()
    }
    return File(path)
}