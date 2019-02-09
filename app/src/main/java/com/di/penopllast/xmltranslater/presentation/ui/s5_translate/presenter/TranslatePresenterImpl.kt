package com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter

import com.di.penopllast.xmltranslater.domain.helper.translate.MessageCallback
import com.di.penopllast.xmltranslater.domain.helper.translate.MessageType
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.impl.TranslateHelperXml
import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.model.LogColor
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.view.TranslateFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class TranslatePresenterImpl(private val view: TranslateFragment? = null)
    : BasePresenter(), TranslatePresenter, MessageCallback {

    private lateinit var currentLocale: String

    override fun generalTranslate() {
        GlobalScope.launch {
            val apiKey = repositoryPreference.getApiKey()
            val fileLocale = repositoryPreference.getFileLocale()
            val filePath = repositoryPreference.getFilePath()
            val translateLocaleList = repositoryDb.getSelectedLocales()
            val fileContent = File(filePath).readText(Charsets.UTF_8)
            val translateHelper: TranslateHelper = TranslateHelperXml(
                    apiKey, fileContent, fileLocale, repositoryNetwork,
                    this@TranslatePresenterImpl)
            translateLocaleList.forEach { locale ->
                currentLocale = locale
                val translatedContent = translateHelper.getTranslatedContent(locale)
                saveFile(translatedContent, filePath, locale, translateHelper.getFileExtension())
            }
        }
    }

    private fun saveFile(translatedContent: String, originalFilePath: String,
                         locale: String, fileExtension: String) {
        val lastSeparatorIndex = originalFilePath.lastIndexOf("/")
        var extensionIndex = originalFilePath.lastIndexOf(".", lastSeparatorIndex)
        val extension = if (extensionIndex != -1)
            originalFilePath.substring(extensionIndex) else fileExtension
        extensionIndex = if (extensionIndex != -1) extensionIndex else originalFilePath.length

        val firstFilePath = originalFilePath.substring(0, extensionIndex - fileExtension.length)

        val fileOut = "$firstFilePath-$locale$extension"
        File(fileOut).writeText(translatedContent)
        view?.addUiLog("File saved as $fileOut", LogColor.GREEN)
    }

    override fun onStatusRetrieved(index: Int, count: Int) {
        view?.updateTranslateStatus(currentLocale, index, count)
    }

    override fun onTranslateMessageRetrieved(message: String, type: Int) {
        view?.addUiLog(message, getMessageColor(type))
    }

    private fun getMessageColor(@MessageType int: Int) = when (int) {
        MessageType.OK -> LogColor.GREEN
        MessageType.NOTIFY -> LogColor.YELLOW
        MessageType.ERROR -> LogColor.RED
        else -> LogColor.DEFAULT
    }
}