package com.di.penopllast.xmltranslater.domain.helper.translate.impl

import android.util.Log
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageCallback
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageType
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.Translater

class TranslateHelperXml(
        private val apiKey: String,
        private val originalContent: String,
        private val originalLocale: String,
        private val translater: Translater,
        private val messageCallback: MessageCallback? = null
) : TranslateHelper {
    companion object {
        private const val TAG = "TranslateHelperXml"
        private const val STRING_TAG = "<string"
        private const val STRING_END = "</string>"
        private const val NAME_TAG = "name=\""
        private const val QUATATION = "\""
        private const val CLOSE_BRACKET = "\">"
        private const val NOT_TRANSLATABLE = "translatable=\"false\""
    }

    override fun getTranslatedContent(toLocale: String): String {

        var translateContent = originalContent
        val stringCount = getCountOfStrings(originalContent)

        var i = -1
        var lastTagIndex = 0
        while (lastTagIndex < translateContent.length) {
            i++
            //init index and text to translate
            val stringIndex = translateContent.indexOf(STRING_TAG, lastTagIndex)
            val startNameIndex = translateContent.indexOf(NAME_TAG, stringIndex)
            val endNameIndex = translateContent.indexOf(QUATATION,
                    startNameIndex + NAME_TAG.length)
            val name = translateContent.substring(startNameIndex, endNameIndex)
            val startTextIndex = translateContent.indexOf(CLOSE_BRACKET, startNameIndex) + 2
            val endTextIndex = translateContent.indexOf(STRING_END, startTextIndex)
            val textToTranslate = translateContent.substring(startTextIndex, endTextIndex)

            if (stringIndex == -1 || startNameIndex == -1
                    || startTextIndex == -1 || endTextIndex == -1) {
                val debugMessage =
                        "Null index: " +
                                "stringIndex = $stringIndex " +
                                "nameIndex = $startNameIndex " +
                                "startTextIndex = $startTextIndex " +
                                "endTextIndex = $endTextIndex " +
                                "textToTranslate = $textToTranslate " +
                                "lastTagIndex = $lastTagIndex "
                Log.d(TAG, debugMessage)
                break
            }

            //check is text with not translatable flag
            val noTranslatable = translateContent.indexOf(NOT_TRANSLATABLE, stringIndex)
            if (noTranslatable != -1 && noTranslatable < startTextIndex) {
                messageCallback?.onTranslateMessageRetrieved("String value of $name " +
                        "with not translatable flag", MessageType.NOTIFY)
                lastTagIndex = translateContent.indexOf("</string>", startTextIndex)
                continue
            }

            //translate
            val translatedText = translater.translate(
                    apiKey,
                    textToTranslate,
                    "$originalLocale-$toLocale")

            //translate checl
            if (translatedText == textToTranslate) {
                messageCallback?.onTranslateMessageRetrieved("Error to translate $name",
                        MessageType.ERROR)
            }

            //put translate result
            translateContent = translateContent.replaceRange(
                    startTextIndex, endTextIndex, translatedText)
            lastTagIndex = translateContent.indexOf("</string>", startTextIndex)

            messageCallback?.onStatusRetrieved(i, stringCount)
        }
        return translateContent
    }

    private fun getCountOfStrings(text: String): Int {
        val endStringTag = "</string>"
        return (text.length - text.replace(endStringTag, "").length) /
                endStringTag.length
    }

    override fun getFileExtension(): String {
        return ".xml"
    }

}