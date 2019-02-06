package com.di.penopllast.xmltranslater.presentation.controller.presenter

import com.di.penopllast.xmltranslater.presentation.controller.view.MainView

class MainPresenterImpl(val view: MainView) : BasePresenter(), MainPresenter {

    override fun isApiKeyExist(): Boolean = !repositoryPreference.getApiKey().isEmpty()

}