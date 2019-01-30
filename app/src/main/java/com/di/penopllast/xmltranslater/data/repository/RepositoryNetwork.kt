package com.di.penopllast.xmltranslater.data.repository

import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter.TranslatePresenter

interface RepositoryNetwork {
    fun getLangList(s: String, apiKey: String, callback: ChooseLanguagePresenter.DownloadCallback)
    fun translate(apiKey: String, key: String, text: String, fromTo: String,
                  callback: TranslatePresenter.TranslateCallback)
}