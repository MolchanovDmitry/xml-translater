package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.repository.impl.RepositoryNetworkImpl
import com.di.penopllast.xmltranslater.di.module.ApiModule
import com.di.penopllast.xmltranslater.di.module.ContextModule
import com.di.penopllast.xmltranslater.di.module.RepositoryModule
import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component(modules = arrayOf(ContextModule::class, RepositoryModule::class, ApiModule::class))
interface AppComponent {

    fun inject(repositoryNetwork: RepositoryNetworkImpl)

    fun inject(basePresenter: BasePresenter)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: XmlTranslaterApp): Builder

        fun build(): AppComponent
    }
}
