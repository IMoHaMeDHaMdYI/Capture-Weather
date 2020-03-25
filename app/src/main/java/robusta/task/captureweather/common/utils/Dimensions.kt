package robusta.task.captureweather.common.utils

import android.content.Context
import android.util.TypedValue

fun spToPx(sp: Float, context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp,
    context.resources.displayMetrics
)