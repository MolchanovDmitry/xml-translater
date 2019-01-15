package com.di.penopllast.xmltranslater.data.repository.impl

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.api.YandexApi
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import javax.inject.Inject

class RepositoryNetworkImpl : RepositoryNetwork {

    private lateinit var yandexApi: YandexApi @Inject set

    init {
        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)
    }
}