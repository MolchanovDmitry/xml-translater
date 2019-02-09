package com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter

import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs

interface ChooseLanguagePresenter {
    fun getLangList()
    fun saveSelectedLocale(locale: String)

    interface DownloadCallback {
        fun onLanguageListFetched(rootLangs: RootLangs)
        fun onLoadError()
    }

}