package com.di.penopllast.xmltranslater.presentation.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.di.penopllast.xmltranslater.R

class RingView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = resources.getDimension(R.dimen.ring_stroke_width)

        colorOrange()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(width / 2.0f, height / 2.0f, (width - 5) / 2.0f, paint)
    }

    fun colorOrange() {
        val color1 = Color.RED
        val color2 = context.getColor(R.color.orange)
        val width = resources.getDimension(R.dimen.ring_width)
        paint.shader = LinearGradient(0f, 0f, width / 2F, width / 2f,
                color1, color2, Shader.TileMode.CLAMP)
    }

    fun colorGreen() {
        val color1 = context.getColor(R.color.orange)
        val color2 = Color.GREEN
        val width = resources.getDimension(R.dimen.ring_width)
        paint.shader = LinearGradient(0f, 0f, width / 2F, width / 2f,
                color1, color2, Shader.TileMode.CLAMP)
    }

}