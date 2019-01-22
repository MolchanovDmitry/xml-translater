package com.di.penopllast.xmltranslater.data.repository

import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter.ChooseLanguagePresenter

interface RepositoryNetwork {
    fun getLangList(s: String, apiKey: String, callback: ChooseLanguagePresenter.DownloadCallback)
    fun translate(apiKey: String, key: String, text: String, fromTo: String,
                  callback: MainPresenter.TranslateCallback)
}