package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.MainActivity
import com.di.penopllast.xmltranslater.application.XmlTranslaterApp

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: XmlTranslaterApp): Builder

        fun build(): AppComponent
    }
}
