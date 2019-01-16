package com.di.penopllast.xmltranslater.data.repository

import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenter

interface RepositoryNetwork {
    fun getLangList(s: String, apiKey: String, callback: MainPresenter.DownloadLanguageCallback)
}