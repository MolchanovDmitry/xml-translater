package com.di.penopllast.xmltranslater.di

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp

class ComponentsHolder (private val context: XmlTranslaterApp) {
    internal lateinit var appComponent: AppComponent

    fun init() {
        appComponent = DaggerAppComponent
                .builder()
                .application(context)
                .build()
    }

}