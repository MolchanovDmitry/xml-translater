package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view

import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.SelectLanguageCallback

interface SelectLanguagesCallback : SelectLanguageCallback{
    fun onUnLanguageSelected(locale: String)
}