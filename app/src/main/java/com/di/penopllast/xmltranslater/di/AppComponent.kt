package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
class AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: XmlTranslaterApp): Builder

        fun build(): AppComponent
    }

}