package com.di.penopllast.xmltranslater.presentation.ui

import androidx.annotation.StringDef

@StringDef
annotation class Fragment {
    companion object {
        const val CHOOSE_FILE = "ChooseFileFragment"
        const val CHOOSE_LANGUAGE = "ChooseLanguageFragment"
        const val CHOOSE_TRANSLATION_LANGUAGE = "ChooseTranslateLanguagesFragment"
        const val TRANSLATE = "TranslateFragment"
    }
}