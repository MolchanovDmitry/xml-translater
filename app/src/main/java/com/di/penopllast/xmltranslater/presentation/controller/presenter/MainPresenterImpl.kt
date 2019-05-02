package com.di.penopllast.xmltranslater.presentation.controller.presenter

import com.di.penopllast.xmltranslater.presentation.controller.view.MainView
import kotlinx.coroutines.launch
import java.util.*

class MainPresenterImpl(val view: MainView?) : BasePresenter(), MainPresenter {
    override fun saveUserLocale(locale: Locale) {
        scopeIO.launch {
            repositoryPreference.setUserLocale(locale.language ?: "en")
        }
    }

    override fun isApiKeyExist() {
        if (repositoryPreference.getApiKey().isNotEmpty()) {
            view?.showChooseFileFragment()
        }
    }
}