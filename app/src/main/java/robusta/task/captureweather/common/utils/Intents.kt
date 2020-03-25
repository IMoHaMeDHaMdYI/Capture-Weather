package robusta.task.captureweather.common.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File


fun shareTwitter(context: Context, authority: String, dir: String): Intent {
    return shareImage(context, authority, dir, "com.twitter.android")
}

fun shareFacebook(context: Context, authority: String, dir: String): Intent {
    return shareImage(context, authority, dir, "com.facebook.katana")

}

fun shareImage(context: Context, authority: String, dir: String, packageName: String): Intent {
    val nonNullFile = File(dir)
    val imageIntent = Intent(Intent.ACTION_SEND)
    imageIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    val path = FileProvider.getUriForFile(
        context,
        authority,
        nonNullFile
    )
    imageIntent.setPackage(packageName);
    imageIntent.setDataAndType(path, "image/png")
    return imageIntent
}