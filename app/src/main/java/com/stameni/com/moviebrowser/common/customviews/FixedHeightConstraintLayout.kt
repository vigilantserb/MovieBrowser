package com.stameni.com.moviebrowser.common.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.stameni.com.moviebrowser.R

class FixedHeightConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var targetPercentage: Float = 0.15f  // Default to 15%

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FixedHeightConstraintLayout,
            0, 0).apply {
            try {
                targetPercentage = getFloat(R.styleable.FixedHeightConstraintLayout_targetHeightPercentage, 0.15f)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val screenHeight = context.resources.displayMetrics.heightPixels
        val desiredHeight = (screenHeight * targetPercentage).toInt()
        val width = MeasureSpec.getSize(widthMeasureSpec)

        // Measure children with the desired height
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        setMeasuredDimension(width, desiredHeight)
    }
}
