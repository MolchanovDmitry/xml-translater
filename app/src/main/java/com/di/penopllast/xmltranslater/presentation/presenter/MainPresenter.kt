package com.di.penopllast.xmltranslater.presentation.presenter

import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs

interface MainPresenter {
    fun getLangList()
    fun translate()

    interface DownloadLanguageCallback {
        fun onLanguageListFetched(rootLangs: RootLangs)
    }

    interface TranslateCallback {
        fun onTranslated(name: String, translatedText: String)
        fun onTranslateError(name: String, text: String)
    }
}