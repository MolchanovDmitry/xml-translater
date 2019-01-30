package com.di.penopllast.xmltranslater.presentation.ui.translate.view

import android.util.ArrayMap

interface TranslateFragment {
    fun showToast(s: String)
    fun updateTranslateStatus(locale: String, index: Int, count: Int)
    fun addUiLog(message: String)
}