package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter

import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs
import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view.ChooseLanguageFragment

class ChooseLanguagePresenterImpl(private val view: ChooseLanguageFragment? = null)
    : BasePresenter(), ChooseLanguagePresenter, ChooseLanguagePresenter.DownloadCallback {

    private val currentLocale = "ru"

    override fun getLangList() {
        repositoryNetwork.getLangList(currentLocale, Const.API_KEY, this)
    }

    override fun onLanguageListFetched(rootLangs: RootLangs) {
        view?.showLanguageList(rootLangs.langs)
    }
}