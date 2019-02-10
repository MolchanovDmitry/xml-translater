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
        private const val QUATATION_MARK = "\""
        private const val CLOSE_BRACKET = "\">"
        private const val NOT_TRANSLATABLE = "translatable=\"false\""
    }

    override fun getTranslatedContent(toLocale: String): String {

        var content = originalContent
        val stringCount = getCountOfStrings(originalContent)

        if (stringCount == 0) {
            messageCallback?.onStatusRetrieved(0, 0)
            messageCallback?.onTranslateMessageRetrieved("There's no strings rows. " +
                    "Check the file", MessageType.ERROR)
            return ""
        }

        var i = -1
        var lastTagIndex = 0
        while (lastTagIndex < content.length) {
            i++
            //init index and text to translate
            val stringIndex = content.indexOf(STRING_TAG, lastTagIndex)
            val keyIndex = content.indexOf(NAME_TAG, stringIndex)
            val keyEndIndex = content.indexOf(QUATATION_MARK,
                    keyIndex + NAME_TAG.length)
            val key = content.substring(keyIndex, keyEndIndex)
            val valueIndex = content.indexOf(CLOSE_BRACKET, keyIndex) + 2
            val valueEndIndex = content.indexOf(STRING_END, valueIndex)
            val value = content.substring(valueIndex, valueEndIndex)

            if (stringIndex == -1 || keyIndex == -1
                    || valueIndex == -1 || valueEndIndex == -1) {
                val debugMessage =
                        "Null index: " +
                                "stringIndex = $stringIndex " +
                                "nameIndex = $keyIndex " +
                                "startTextIndex = $valueIndex " +
                                "endTextIndex = $valueEndIndex " +
                                "textToTranslate = $value " +
                                "lastTagIndex = $lastTagIndex "
                Log.d(TAG, debugMessage)
                break
            }

            //check is text with not translatable flag
            val noTranslatable = content.indexOf(NOT_TRANSLATABLE, stringIndex)
            if (noTranslatable != -1 && noTranslatable < valueIndex) {
                messageCallback?.onTranslateMessageRetrieved("String value of $key " +
                        "with not translatable flag", MessageType.NOTIFY)
                lastTagIndex = content.indexOf("</string>", valueIndex)
                continue
            }

            //translate
            val translatedText = translater.translate(
                    apiKey,
                    value,
                    "$originalLocale-$toLocale")

            //translate check
            if (translatedText == value) {
                messageCallback?.onTranslateMessageRetrieved("Error to translate $key",
                        MessageType.ERROR)
            }

            //put translate result
            content = content.replaceRange(
                    valueIndex, valueEndIndex, translatedText)
            lastTagIndex = content.indexOf("</string>", valueIndex)

            messageCallback?.onStatusRetrieved(i, stringCount)
        }
        return content
    }

    private fun getCountOfStrings(text: String): Int {
        val endStringTag = "STRING_END"
        return (text.length - text.replace(endStringTag, "").length) /
                endStringTag.length
    }

    override fun getFileExtension(): String {
        return ".xml"
    }

}