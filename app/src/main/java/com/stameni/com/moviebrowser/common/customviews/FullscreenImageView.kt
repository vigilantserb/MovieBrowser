package com.stameni.com.moviebrowser.common.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class FullscreenImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val screenHeight = context.resources.displayMetrics.heightPixels
        val desiredHeight = (screenHeight * 0.85).toInt()
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, desiredHeight)
    }
}
