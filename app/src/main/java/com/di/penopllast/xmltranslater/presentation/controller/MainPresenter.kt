package com.di.penopllast.xmltranslater.presentation.controller

interface MainPresenter {
    fun saveFilePath(path: String)
    fun isApiKeyExist(): Boolean
}