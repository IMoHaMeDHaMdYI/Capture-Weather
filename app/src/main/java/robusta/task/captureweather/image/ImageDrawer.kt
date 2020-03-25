package robusta.task.captureweather.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.graphics.withTranslation
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.utils.spToPx

class ImageDrawer {
    private val imagePaint = Paint().apply {
        strokeWidth = 20f
        style = Paint.Style.FILL_AND_STROKE
    }
    private val textPaint = TextPaint()

    fun draw(bitmap: Bitmap, text: String, context: Context): Bitmap {
        val canvas = Canvas(bitmap)
        textPaint.textSize = spToPx(24f, context)
        canvas.drawBitmap(bitmap, 0f, 0f, imagePaint)
        Log.d(TAG, text)
        val textLayout = createStaticLayout(text, 0, text.length, bitmap.width)
        textLayout.let {
            val textLeft = (bitmap.width - it.width) / 2f
            val textTop = (bitmap.height - it.height) / 2f
            it.draw(canvas, textLeft, textTop)
        }
        return bitmap
    }

    private fun createStaticLayout(
        text: String,
        start: Int,
        end: Int,
        width: Int,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_CENTER
    ): StaticLayout {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createStaticLayoutAfterM(text, start, end, width, alignment)
        } else {
            createStaticLayoutBeforeM(text, start, end, width, alignment)
        }
    }

    @Suppress("DEPRECATION")
    private fun createStaticLayoutBeforeM(
        text: String,
        start: Int,
        end: Int,
        width: Int,
        alignment: Layout.Alignment
    ) = StaticLayout(text, start, end, textPaint, width, alignment, 1f, 1f, true)

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createStaticLayoutAfterM(
        text: String,
        start: Int,
        end: Int,
        width: Int,
        alignment: Layout.Alignment
    ) = StaticLayout.Builder
        .obtain(text, start, end, textPaint, width)
        .setAlignment(alignment)
        .build()

    private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
        canvas.withTranslation(x, y) {
            draw(this)
        }
    }

}