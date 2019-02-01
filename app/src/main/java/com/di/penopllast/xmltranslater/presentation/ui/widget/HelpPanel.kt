package com.di.penopllast.xmltranslater.presentation.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.di.penopllast.xmltranslater.R
import kotlinx.android.synthetic.main.vidget_help_panel.view.*

class HelpPanel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    
    private val ringList = ArrayList<View>()
    private val numbList = ArrayList<View>()
    private val hintList = ArrayList<View>()

    init {
        inflate(context, R.layout.vidget_help_panel, this)

        hintList.add(text_1_description)
        hintList.add(text_2_description)
        hintList.add(text_3_description)
        hintList.add(text_4_description)
        hintList.add(text_5_description)
    }

    public fun hide() {
        val ringWidth = ring_1.width
        ring_1.animate().translationX(-50f)
        /*animate().translationX(-ringWidth + ringWidth * 0.15f).withEndAction {
            hintList.forEach { it.visibility = GONE }
        }*/
    }


    private fun hideRings(){

    }

}