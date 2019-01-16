package com.di.penopllast.xmltranslater.presentation.presenter

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import javax.inject.Inject

open class BasePresenter {

    lateinit var repositoryNetwork: RepositoryNetwork @Inject set

    init {
        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)
    }
}