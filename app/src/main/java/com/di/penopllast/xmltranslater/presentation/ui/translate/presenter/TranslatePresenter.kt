package com.di.penopllast.xmltranslater.presentation.ui.translate.presenter

interface TranslatePresenter {
    fun generalTranslate()

    interface TranslateCallback {
        fun onTranslated(name: String, translatedText: String)
        fun onTranslateError(name: String, text: String)
    }
}