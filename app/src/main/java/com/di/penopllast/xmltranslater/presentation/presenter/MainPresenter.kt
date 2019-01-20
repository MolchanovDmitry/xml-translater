package com.di.penopllast.xmltranslater.presentation.presenter

import com.google.gson.internal.LinkedTreeMap

interface MainPresenter {
    fun getLangs()
    fun translate()

    interface DownloadLanguageCallback {
        fun onLanguageListFetched(langs: LinkedTreeMap<String, String>)
    }

    interface TranslateCallback {
        fun onTranslated(key: String?, translatedValue: String)
    }
}