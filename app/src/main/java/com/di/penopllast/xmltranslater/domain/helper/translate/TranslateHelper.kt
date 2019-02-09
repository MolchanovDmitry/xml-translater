package com.di.penopllast.xmltranslater.domain.helper.translate

import androidx.annotation.IntDef

interface TranslateHelper {
    fun getTranslatedContent(toLocale: String): String
    fun getFileExtension(): String
}