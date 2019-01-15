package com.di.penopllast.xmltranslater.application

import android.app.Application
import com.di.penopllast.xmltranslater.di.ComponentsHolder

class XmlTranslaterApp : Application() {

    internal lateinit var componentsHolder: ComponentsHolder

    override fun onCreate() {
        super.onCreate()
        app = this
        componentsHolder = ComponentsHolder(this)
        componentsHolder.init()
    }

    companion object {
        private lateinit var app: XmlTranslaterApp
    }
}