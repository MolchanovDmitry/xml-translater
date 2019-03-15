package com.di.penopllast.xmltranslater.presentation.controller.connector

interface TranslateConnector {
    fun onTranslateFinish(resultFilePathList: ArrayList<String>)
    fun onTranslateScreenCancel()
}