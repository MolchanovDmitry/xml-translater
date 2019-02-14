package com.di.penopllast.xmltranslater.domain.helper.translate.impl

import com.di.penopllast.xmltranslater.domain.helper.translate.MessageCallback
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageType
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.Translater
import java.util.regex.Pattern

class TranslateHelperIosStrings(
        private val apiKey: String,
        private val originalContent: String,
        private val originalLocale: String,
        private val translater: Translater,
        private val messageCallback: MessageCallback? = null
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

        println("1488 count = $textCount")
        var valueEndIndex = 0
        for (i in 0..textCount) {
            val keyStartIndex = content.indexOf(REGEX_ROW, valueEndIndex)
            val keyEndIndex = content.indexOf(REGEX_DIVIDER, keyStartIndex)
            val equallyIndex = content.indexOf("=", keyEndIndex)
            val valueStartIndex = content.indexOf(QUOTE, equallyIndex)
            valueEndIndex = content.indexOf(REGEX_END_ROW, valueStartIndex)

            val key = content.substring(keyStartIndex, keyEndIndex)
            val value = content.substring(valueStartIndex, valueEndIndex)

            println("1488 key = $key value = $value")

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
        }
    }

    override fun getFileExtension(): String {
        return ".strings"
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