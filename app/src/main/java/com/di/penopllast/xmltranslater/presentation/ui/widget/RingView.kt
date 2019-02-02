package com.di.penopllast.xmltranslater.presentation.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.di.penopllast.xmltranslater.R

class RingView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ringPaint = Paint()

    init {
        ringPaint.isAntiAlias = true;
        ringPaint.isFilterBitmap = true;
        ringPaint.color = context.getColor(R.color.orange) // Your color here
        ringPaint.style = Paint.Style.STROKE // This is the important line
        ringPaint.strokeWidth = 5f // Your stroke width in pixels

        val color1 = getContext().getColor(R.color.orange);
        val color2 = getContext().getColor(R.color.primary_dark_material_dark)
        ringPaint.setShader(LinearGradient(0f, 0f, 100f, 100f,
                color1, color2, Shader.TileMode.CLAMP))
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(width / 2.0f, height / 2.0f, (width - 5) / 2.0f, ringPaint)
    }

}