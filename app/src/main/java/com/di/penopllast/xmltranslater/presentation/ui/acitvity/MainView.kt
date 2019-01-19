package com.di.penopllast.xmltranslater.presentation.ui.acitvity

import com.google.gson.internal.LinkedTreeMap

interface MainView {
    fun onLanguageListFetched(langs: LinkedTreeMap<String, String>)
    fun onTranslateComplete()
}