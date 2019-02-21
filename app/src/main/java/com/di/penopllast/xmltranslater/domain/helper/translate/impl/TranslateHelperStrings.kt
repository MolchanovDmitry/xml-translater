package com.di.penopllast.xmltranslater.domain.helper.translate.impl

import com.di.penopllast.xmltranslater.domain.helper.translate.MessageCallback
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageType
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.Translater
import java.util.regex.Pattern

class TranslateHelperStrings(
        private val apiKey: String,
        private val originalContent: String,
        private val originalLocale: String,
        private val translater: Translater,
        private val messageCallback: MessageCallback?
) : TranslateHelper {

    companion object {
        private const val REGEX_ROW = "^.*\"[ ]*=[ ]*\".*;$"
        private const val REGEX_END_ROW = "\".*;$"
        private const val REGEX_DIVIDER = "\"[ ]*=[ ]*\""
        private const val QUOTE = "\""
    }

    override fun getTranslatedContent(toLocale: String): String {
        val textCount = getCountOfText()
        var content = originalContent

        var valueEndIndex = 0
        for (i in 0..textCount) {
            val keyStartIndex = indexOfRegex(REGEX_ROW, content, valueEndIndex) + 1
            val keyEndIndex = indexOfRegex(REGEX_DIVIDER, content, keyStartIndex)
            val equallyIndex = content.indexOf("=", keyEndIndex)
            val valueStartIndex = content.indexOf(QUOTE, equallyIndex) + 1
            valueEndIndex = indexOfRegex(REGEX_END_ROW, content, valueStartIndex)

            val key = content.substring(keyStartIndex, keyEndIndex)
            val value = content.substring(valueStartIndex, valueEndIndex)

            val translatedText = translater.translate(
                    apiKey, value, "$originalLocale-$toLocale")
            if (translatedText == value) {
                messageCallback?.onTranslateMessageRetrieved("Error to translate $key",
                        MessageType.ERROR)
            }

            //put translate result
            content = content.replaceRange(
                    valueStartIndex, valueEndIndex, translatedText)
            messageCallback?.onStatusRetrieved(i, textCount)

            valueEndIndex = equallyIndex
        }
        return content
    }

    override fun getFileExtension(): String {
        return ".strings"
    }

    private fun indexOfRegex(regex: String, s: String, startIndex: Int): Int {
        val pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val matcher = pattern.matcher(s)
        return if (matcher.find(startIndex)) matcher.start() else -1
    }

    private fun getCountOfText(): Int {
        val regex = Regex(REGEX_DIVIDER)
        val simplyRegex = "\"=\""
        val content = originalContent.replace(regex, simplyRegex)
        val pattern = Pattern.compile(simplyRegex)
        val matcher = pattern.matcher(content)

        var count = 0
        while (matcher.find())
            count++
        return count
    }
}