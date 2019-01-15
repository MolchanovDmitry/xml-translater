package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.MainActivity
import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.repository.impl.RepositoryNetworkImpl

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(repositoryNetwork: RepositoryNetworkImpl)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: XmlTranslaterApp): Builder

        fun build(): AppComponent
    }
}
