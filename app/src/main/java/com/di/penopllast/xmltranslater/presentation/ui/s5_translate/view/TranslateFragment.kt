package com.di.penopllast.xmltranslater.presentation.ui.s5_translate.view

import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.model.LogColor

interface TranslateFragment {
    fun showToast(s: String)
    fun updateTranslateStatus(locale: String, index: Int, count: Int)
    fun addUiLog(message: String, @LogColor color: Int = LogColor.DEFAULT)
}