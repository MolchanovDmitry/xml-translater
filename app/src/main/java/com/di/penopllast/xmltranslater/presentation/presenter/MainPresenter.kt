package com.di.penopllast.xmltranslater.presentation.presenter

import com.google.gson.internal.LinkedTreeMap

interface MainPresenter {
    fun getLangs()

    interface DownloadLanguageCallback{
        fun onLanguageListFetched(langs: LinkedTreeMap<String, String>)
    }
}