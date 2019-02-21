package com.di.penopllast.xmltranslater.domain.helper.translate.impl

import com.di.penopllast.xmltranslater.domain.helper.translate.MessageCallback
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageType
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.Translater

class TranslateHelperPhp(
        private val apiKey: String,
        private val originalContent: String,
        private val originalLocale: String,
        private val translater: Translater,
        private val messageCallback: MessageCallback?
) : TranslateHelper {

    companion object {
        private const val ARROW = "=>"
        private const val QUOTE = "\'"
        private const val START_ARRAY = "=> ["
    }

    /**
     * 'key'  =>(arrow) 'value'
     */
    override fun getTranslatedContent(toLocale: String): String {
        val countText = getCountOfText(originalContent)
        if (countText == 0) {
            messageCallback?.onStatusRetrieved(0, 0)
            messageCallback?.onTranslateMessageRetrieved("There is no text. " +
                    "Check the file", MessageType.ERROR)
        }

        var content = originalContent
        var lastArrowIndex = 0
        var lastValueEndIndex = 0
        var i = 0
        while (i < countText) {
            val arrowIndex = content.indexOf(ARROW, lastArrowIndex + ARROW.length)
            val keyIndex = content.indexOf(QUOTE, lastValueEndIndex + 1)
            val keyEndIndex = content.indexOf(QUOTE, keyIndex + 1)
            val valueIndex = content.indexOf(QUOTE, arrowIndex)
            val valueEndIndex = content.indexOf(QUOTE, valueIndex + 1)
            val startArrayIndex = content.indexOf(START_ARRAY, keyIndex)
            lastArrowIndex = arrowIndex

            if (startArrayIndex != -1 && startArrayIndex < valueIndex) {
                lastValueEndIndex = lastArrowIndex
                continue
            }

            val key = content.substring(keyIndex, keyEndIndex)
            val value = content.substring(valueIndex, valueEndIndex)

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

            lastValueEndIndex = valueEndIndex
            i++
            messageCallback?.onStatusRetrieved(i, countText)
        }
        return content
    }

    override fun getFileExtension(): String {
        return ".php"
    }

    private fun getCountOfText(text: String): Int {
        val regex = Regex(".*'action'.*=> \\[")
        val content = text.replace(regex, "")
        val endStringTag = "=> \'"
        return (content.length - content.replace(endStringTag, "").length) /
                endStringTag.length
    }
}