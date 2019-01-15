package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp

class ComponentHolder (private val context: XmlTranslaterApp) {
    var appComponent: AppComponent? = null
        private set

    fun init() {
        appComponent = DaggerAppComponent
                .builder()
                .application(context)
                .build()
    }

}