package com.di.penopllast.xmltranslater.presentation.ui.translate.presenter

import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser.XmlToJson
import com.di.penopllast.xmltranslater.domain.model.string.StringRoot
import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.translate.model.LogColor
import com.di.penopllast.xmltranslater.presentation.ui.translate.view.TranslateFragment
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.text.StringBuilder

class TranslatePresenterImpl(private val view: TranslateFragment? = null)
    : BasePresenter(), TranslatePresenter, TranslatePresenter.TranslateCallback {

    private lateinit var originalXml: StringBuilder
    private var currentTranslateLocale = ""
    private var filePathWithoutName = ""
    private var stringRowCount = 0
    private var iterationIndex = 0

    override fun generalTranslate() {
        GlobalScope.launch {
            val fileLocale = repositoryPreference.getFileLocale()
            val filePath = repositoryPreference.getFilePath()
            val translateLocaleList = repositoryDb.getSelectedLocales()

            /*val fileLocale = "ru"
            val filePath = "/sdcard/strings.xml"
            val translateLocaleList = ArrayList<String>()
            translateLocaleList.add("en")
            translateLocaleList.add("de")*/

            filePathWithoutName = filePath.substring(0, filePath.lastIndexOf('/'))
            translateLocaleList.forEach {
                currentTranslateLocale = it
                iterationIndex = 0
                translate(filePath, fileLocale, it)
            }
        }
    }

    private fun translate(filePath: String, fromLocale: String, toLocale: String) {
        val stringRoot: StringRoot? = parseXmlFile(filePath)
        val stringCount = stringRoot?.resources?.string?.size
        if (stringCount == 0) {
            view?.addUiLog("Nothing to translate in $filePath", LogColor.RED)
        } else {
            view?.addUiLog("Beginning translate from \"$fromLocale\" to \"$toLocale\" ")
            stringRoot?.resources?.string?.let {
                it.forEach { stringRow ->
                    stringRowCount = it.size

                    var isTranslatable = true
                    stringRow.translatable?.let { translatable ->
                        if (translatable == "false") {
                            isTranslatable = false
                            iterationIndex++
                        }
                    }
                    if (isTranslatable) {
                        repositoryNetwork.translate(
                                Const.API_KEY,
                                stringRow.name,
                                stringRow.content,
                                "$fromLocale-$toLocale",
                                this)
                    } else {
                        view?.addUiLog("Not translatable field: ${stringRow.name}", LogColor.YELLOW)
                    }
                }
            }
        }
        view?.addUiLog("Translate from \"$fromLocale\" to \"$toLocale\" success.", LogColor.GREEN)
        saveXmlFile()
    }

    private fun parseXmlFile(filePath: String): StringRoot? {
        val xml = File(filePath).readText(Charsets.UTF_8)
        originalXml = StringBuilder(xml)
        val xmlToJson = XmlToJson.Builder(xml).build()
        val jsonObject = xmlToJson.toJson()
        return Gson().fromJson(jsonObject?.toString(), StringRoot::class.java)
    }

    override fun onTranslated(name: String, translatedText: String) {
        iterationIndex++

        val tagName = "name=\"$name\""
        val tagIndex = originalXml.indexOf(tagName)
        val startReplaceIndex = originalXml.indexOf(">", tagIndex) + 1
        val endReplaceIndex = originalXml.indexOf("</string>", startReplaceIndex)
        originalXml.replace(startReplaceIndex, endReplaceIndex, translatedText)

        view?.updateTranslateStatus(currentTranslateLocale, iterationIndex, stringRowCount)
    }

    override fun onTranslateError(name: String, text: String) {
        iterationIndex++
        view?.addUiLog("Not translated field: $name", LogColor.RED)
    }

    private fun saveXmlFile() {
        val fileOut = "$filePathWithoutName/string-$currentTranslateLocale.xml"
        File(fileOut).writeText(originalXml.toString())
        view?.addUiLog("File saved as $fileOut", LogColor.GREEN)
    }
}