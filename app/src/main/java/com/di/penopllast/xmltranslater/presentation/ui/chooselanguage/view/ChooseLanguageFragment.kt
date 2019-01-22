package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view

import com.google.gson.internal.LinkedTreeMap

interface ChooseLanguageFragment {
    fun showLanguageList(langs: LinkedTreeMap<String, String>)
}