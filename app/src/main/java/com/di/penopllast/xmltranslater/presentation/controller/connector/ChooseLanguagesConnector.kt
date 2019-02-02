package com.di.penopllast.xmltranslater.presentation.controller.connector

interface ChooseLanguagesConnector : ChooseLanguageConnector {
    fun onUnLanguageSelected(locale: String)
}