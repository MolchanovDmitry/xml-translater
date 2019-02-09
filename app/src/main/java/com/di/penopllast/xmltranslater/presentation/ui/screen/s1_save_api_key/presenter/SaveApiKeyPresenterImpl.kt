package com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view.SaveApiKeyFragment

class SaveApiKeyPresenterImpl(private val view: SaveApiKeyFragment? = null)
    : BasePresenter(), SaveApiKeyPresenter {

    override fun saveApiKey(key: String) {
        repositoryPreference.setApiKey(key)
        view?.onFinish()
    }

    override fun checkApiKeyExist() {
        val savedKey = repositoryPreference.getApiKey()
        if (savedKey != ""){
            view?.updateApiKey(savedKey)
        }
    }
}