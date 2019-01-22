package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view

import android.util.ArrayMap

interface ChooseLanguageFragment {
    fun showLanguageList(langMap: ArrayMap<String, String>)
}