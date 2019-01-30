package com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.view

import android.util.ArrayMap

interface ChooseLanguageFragment {
    fun showLanguageList(langMap: ArrayMap<String, String>)
    fun onLoadError()
}