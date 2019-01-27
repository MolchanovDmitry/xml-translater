package com.di.penopllast.xmltranslater.presentation.presenter.connector

interface ChooseLanguagesConnector : ChooseLanguageConnector {
    fun onUnLanguageSelected(locale: String)
}