package com.di.penopllast.xmltranslater.presentation.controller.presenter

import com.di.penopllast.xmltranslater.presentation.controller.view.MainView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainPresenterImpl(val view: MainView) : BasePresenter(), MainPresenter {
    override fun saveUserLocale(locale: Locale) {
        GlobalScope.launch {
            repositoryPreference.setUserLocale(locale.language ?: "en")
        }
    }

    override fun isApiKeyExist(): Boolean = !repositoryPreference.getApiKey().isEmpty()

}