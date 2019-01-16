package com.di.penopllast.xmltranslater.presentation.presenter

import com.di.penopllast.xmltranslater.application.utils.Const

class MainPresenterImpl : BasePresenter(), MainPresenter {

    override fun getLangs() {
        repositoryNetwork.getLangList("ru", Const.API_KEY)
    }
}