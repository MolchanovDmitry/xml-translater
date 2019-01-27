package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter

import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs

interface ChooseLanguagePresenter {
    fun getLangList()

    interface DownloadCallback {
        fun onLanguageListFetched(rootLangs: RootLangs)
    }

}