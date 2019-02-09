package com.di.penopllast.xmltranslater.domain.helper.translate

class TranslateHelperXml(
        private val apiKey: String,
        private val originalContent: String,
        private val originalLocale: String,
        private val translater: Translater
) : TranslateHelper {

    override fun getTranslatedContent(toLocale: String): String {

        var translateContent = originalContent

        var lastTagIndex = 0
        while (lastTagIndex < translateContent.length) {

            val stringIndex = translateContent.indexOf("<string", lastTagIndex)
            val nameIndex = translateContent.indexOf("name=\"", stringIndex)
            val startTextIndex = translateContent.indexOf("\">", nameIndex) + 2
            val endTextIndex = translateContent.indexOf("</string>", startTextIndex)

            val textToTranslate = translateContent.substring(startTextIndex, endTextIndex)
            val translatedText = translater.translate(
                    apiKey,
                    textToTranslate,
                    "$originalLocale-$toLocale")
            translateContent = translateContent.replaceRange(startTextIndex, endTextIndex, translatedText)
            lastTagIndex = translateContent.indexOf("</string>", startTextIndex)
        }
        return translateContent
    }

    override fun getFileExtension(): String {
        return ".xml"
    }

}