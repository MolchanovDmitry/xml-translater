package com.di.penopllast.xmltranslater.presentation.controller

class MainPresenterImpl(val view: MainView) : BasePresenter(), MainPresenter {

    override fun saveFilePath(path: String) {
        repositoryPreference.setFilePath(path)
    }

    override fun isApiKeyExist(): Boolean = !repositoryPreference.getApiKey().isEmpty()

}