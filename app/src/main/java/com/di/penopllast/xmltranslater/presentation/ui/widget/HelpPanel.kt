package com.di.penopllast.xmltranslater.presentation.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.di.penopllast.xmltranslater.R
import kotlinx.android.synthetic.main.widget_help_panel.view.*

class HelpPanel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val groupList = ArrayList<View>()
    private val hintList = ArrayList<View>()
    private var isHidden = false
    private var clickListener: OnHelpViewClickListener? = null

    init {
        inflate(context, R.layout.widget_help_panel, this)
        fillLists()
        initClickListeners()
    }

    private fun fillLists() {
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

    private fun initClickListeners() {
        frame_1.setOnClickListener { clickListener?.onFirstStepClick() }
        frame_2.setOnClickListener { clickListener?.onSecondStepClicl() }
        frame_3.setOnClickListener { clickListener?.onThirdStepClick() }
        frame_4.setOnClickListener { clickListener?.onFourthStepClick() }
        frame_4.isClickable = false

        text_1_description.setOnClickListener { clickListener?.onFirstStepClick() }
        text_2_description.setOnClickListener { clickListener?.onSecondStepClicl() }
        text_3_description.setOnClickListener { clickListener?.onThirdStepClick() }
        text_4_description.setOnClickListener { clickListener?.onFourthStepClick() }
        text_4_description.isClickable = false
    }

    fun hide() {
        hideShowRings(true)
        hideShowHints(true)
        hideShowBack(true)
        isHidden = true
    }

    fun show() {
        hideShowRings(false)
        hideShowHints(false)
        hideShowBack(false)
        isHidden = false
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

    fun isHidden(): Boolean = isHidden

    fun setClickListener(listener: OnHelpViewClickListener) {
        this.clickListener = listener
    }

    interface OnHelpViewClickListener {
        fun onFirstStepClick()
        fun onSecondStepClicl()
        fun onThirdStepClick()
        fun onFourthStepClick()
    }
}