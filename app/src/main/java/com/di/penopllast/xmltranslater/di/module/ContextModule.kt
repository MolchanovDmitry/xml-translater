package com.di.penopllast.xmltranslater.di.module

import android.content.Context
import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    internal fun provideContext(app: XmlTranslaterApp): Context {
        return app.applicationContext
    }

}