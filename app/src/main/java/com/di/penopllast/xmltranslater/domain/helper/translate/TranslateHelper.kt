package com.di.penopllast.xmltranslater.domain.helper.translate

interface TranslateHelper {
    fun getTranslatedContent(toLocale: String): String
    fun getFileExtension(): String
}