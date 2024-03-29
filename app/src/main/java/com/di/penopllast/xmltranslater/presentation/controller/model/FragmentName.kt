package com.di.penopllast.xmltranslater.presentation.controller.model

import androidx.annotation.StringDef

@StringDef
annotation class FragmentName {
    companion object {
        const val SAVE_API_KEY = "SaveApiKeyFragmentImpl"
        const val CHOOSE_FILE = "ChooseFileFragmentImpl"
        const val CHOOSE_LANGUAGE = "ChooseLanguageFragment"
        const val CHOOSE_TRANSLATION_LANGUAGE = "ChooseTranslateLanguagesFragment"
        const val TRANSLATE = "TranslateFragment"
    }
}