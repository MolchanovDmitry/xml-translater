package com.di.penopllast.xmltranslater.data.repository

import com.di.penopllast.xmltranslater.domain.helper.translate.Translater
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter.ChooseLanguagePresenter

interface RepositoryNetwork : Translater {
    fun getLangList(s: String, apiKey: String, callback: ChooseLanguagePresenter.DownloadCallback)
}