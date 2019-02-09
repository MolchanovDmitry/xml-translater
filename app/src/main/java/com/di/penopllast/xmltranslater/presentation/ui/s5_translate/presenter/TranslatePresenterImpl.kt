package com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter

import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelper
import com.di.penopllast.xmltranslater.domain.helper.translate.TranslateHelperXml
import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.model.LogColor
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.view.TranslateFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class TranslatePresenterImpl(private val view: TranslateFragment? = null)
    : BasePresenter(), TranslatePresenter {

    override fun generalTranslate() {
        GlobalScope.launch {
            val apiKey = repositoryPreference.getApiKey()
            val fileLocale = repositoryPreference.getFileLocale()
            val filePath = repositoryPreference.getFilePath()
            val translateLocaleList = repositoryDb.getSelectedLocales()
            val fileContent = File(filePath).readText(Charsets.UTF_8)
            val translateHelper: TranslateHelper = TranslateHelperXml(
                    apiKey, fileContent, fileLocale, repositoryNetwork)
            translateLocaleList.forEach { locale ->
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
}