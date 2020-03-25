package robusta.task.captureweather.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.withTranslation
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.utils.spToPx


class TextOverlayImageView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    var bitmap: Bitmap? = null
        set(value) {
            field = value
            invalidate()
        }
    var text: String = ""
        set(value) {
            field = value
            Log.d(TAG, "width : $width")
            invalidate()
        }
    private val imagePaint = Paint().apply {
        strokeWidth = 20f
        style = Paint.Style.FILL_AND_STROKE
    }
    private val textPaint = TextPaint().apply {
        textSize = spToPx(24f, context)
    }

    private var textLayout: StaticLayout? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        //Measure Width
        width = when (widthMode) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                widthSize
            }
            else -> {
                bitmap?.width ?: 200
            }
        }
        //Measure Height
        height =
            if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) { //Must be this size
                heightSize
            } else { //Be whatever you want
                bitmap?.height ?: 200
            }
        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let { bitmap ->
            val bitmapLeft = (width - bitmap.width) / 2f
            val bitmapTop = (height - bitmap.height) / 2f
            canvas.drawBitmap(bitmap, bitmapLeft, bitmapTop, imagePaint)
            Log.d(TAG, text)
            textLayout = createStaticLayout(text, 0, text.length, width)
            textLayout?.let {
                val textLeft = bitmapLeft + (bitmap.width - it.width) / 2
                Log.d(TAG, "bitmap left = $bitmapLeft ,, ${it.width}")
                val textTop = bitmapTop + (bitmap.height - it.height) / 2
                it.draw(canvas, textLeft, textTop)
            }
        }
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