package com.di.penopllast.xmltranslater.presentation.controller.connector

interface ChooseLanguageConnector : BaseConnector {

    fun onLanguageSelected(locale: String)
}