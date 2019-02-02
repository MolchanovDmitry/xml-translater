package com.di.penopllast.xmltranslater.presentation.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.di.penopllast.xmltranslater.R
import kotlinx.android.synthetic.main.vidget_help_panel.view.*

class HelpPanel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val groupList = ArrayList<View>()
    private val hintList = ArrayList<View>()

    init {
        inflate(context, R.layout.vidget_help_panel, this)

        groupList.add(frame_1)
        groupList.add(frame_2)
        groupList.add(frame_3)
        groupList.add(frame_4)
        groupList.add(frame_5)

        hintList.add(text_1_description)
        hintList.add(text_2_description)
        hintList.add(text_3_description)
        hintList.add(text_4_description)
        hintList.add(text_5_description)
    }

    fun hide() {
        hideShowRings(true)
        hideShowHints(true)
        hideShowBack(true)
    }

    fun show() {
        hideShowRings(false)
        hideShowHints(false)
        hideShowBack(false)
    }


    private fun hideShowRings(isHide: Boolean) {
        val frameWidth = frame_1.width
        val x = if (isHide) -frameWidth + frameWidth * 0.15f else 0F
        groupList.forEachIndexed { index, view ->
            view.animate()
                    .translationX(x)
                    .startDelay = index * 50L
        }
    }

    private fun hideShowHints(isHide: Boolean) {
        val x = if (isHide) -width * 1f else 0F
        hintList.forEachIndexed { index, view ->
            view.animate()
                    .translationX(x)
                    .setDuration(750)
                    .startDelay = 50L + index * 50L
        }
    }

    private fun hideShowBack(isHide: Boolean) {
        val x = if (isHide) -view_background.width + view_background.width * 0.18F else 0F
        view_background.animate()
                .translationX(x)
                .setDuration(750)
                .startDelay = 200
    }


}