package com.di.penopllast.xmltranslater.presentation.presenter

import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.MainView
import com.google.gson.internal.LinkedTreeMap

class MainPresenterImpl(val view: MainView)
    : BasePresenter(), MainPresenter, MainPresenter.DownloadLanguageCallback {

    override fun getLangs() {
        repositoryNetwork.getLangList("ru", Const.API_KEY, this)
    }

    override fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        view.onLanguageListFetched(langs)
    }
}