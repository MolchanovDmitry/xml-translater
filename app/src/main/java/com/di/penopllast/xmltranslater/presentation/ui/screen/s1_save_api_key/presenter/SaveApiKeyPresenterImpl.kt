package com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view.SaveApiKeyFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveApiKeyPresenterImpl(private val view: SaveApiKeyFragment?)
    : BasePresenter(), SaveApiKeyPresenter {

    override fun saveApiKey(key: String) {
        scopeIO.launch {
            repositoryPreference.setApiKey(key)
            withContext(Dispatchers.Main) { view?.onFinish() }
        }
    }

    override fun checkApiKeyExist() {
        scopeIO.launch {
            val savedKey = repositoryPreference.getApiKey()
            if (savedKey != "") {
                withContext(Dispatchers.Main) { view?.updateApiKey(savedKey) }
            }
        }
    }
}