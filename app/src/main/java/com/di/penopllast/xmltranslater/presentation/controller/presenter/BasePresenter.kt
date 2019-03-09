package com.di.penopllast.xmltranslater.presentation.controller.presenter

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.repository.RepositoryDb
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import javax.inject.Inject

open class BasePresenter {

    lateinit var repositoryNetwork: RepositoryNetwork @Inject set
    lateinit var repositoryDb: RepositoryDb @Inject set
    lateinit var repositoryPreference: RepositoryPreference @Inject set

    private val job = SupervisorJob()
    val scopeIO = CoroutineScope(Dispatchers.IO + job)

    init {
        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)
    }
}