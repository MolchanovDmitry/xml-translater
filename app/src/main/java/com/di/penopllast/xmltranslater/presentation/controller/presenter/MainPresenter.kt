package com.di.penopllast.xmltranslater.presentation.controller.presenter

import java.util.*

interface MainPresenter {
    fun isApiKeyExist(): Boolean
    fun saveUserLocale(locale: Locale)
}