package com.di.penopllast.xmltranslater.presentation.ui.translate.presenter

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser.XmlToJson
import com.di.penopllast.xmltranslater.domain.model.string.StringRoot
import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.StatusKey
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
    private var iterationCount: Int = 0

    override fun generalTranslate() {
        GlobalScope.launch {
            val fileLocale = repositoryPreference.getFileLocale()
            val filePath = repositoryPreference.getFilePath()
            val translateLocaleList = repositoryDb.getSelectedLocales()

            filePathWithoutName = filePath.substring(0, filePath.lastIndexOf('/'))
            translateLocaleList.forEach {
                currentTranslateLocale = it
                iterationCount = 0
                translate(filePath, fileLocale, it)
            }
        }
    }

    private fun translate(filePath: String, fromLocale: String, toLocale: String) {

        val stringRoot: StringRoot? = parseXmlFile(filePath)
        stringRoot?.resources?.string?.let {
            loop@ for (stringRow in it) {
                stringRowCount = it.size

                var isTranslatable = true
                stringRow.translatable?.let { translatable ->
                    if (translatable.equals("false")) {
                        isTranslatable = false
                        iterationCount++
                    }
                }
                if (!isTranslatable) continue@loop

                repositoryNetwork.translate(
                        Const.API_KEY,
                        stringRow.name,
                        stringRow.content,
                        "$fromLocale-$toLocale",
                        this)
            }
        }
    }

    private fun parseXmlFile(filePath: String): StringRoot? {
        val xml = File(filePath).readText(Charsets.UTF_8)
        originalXml = StringBuilder(xml)
        val xmlToJson = XmlToJson.Builder(xml).build()
        val jsonObject = xmlToJson.toJson()
        return Gson().fromJson(jsonObject?.toString(), StringRoot::class.java)
    }

    override fun onTranslated(name: String, translatedText: String) {
        iterationCount++

        val tagName = "name=\"$name\""
        val tagIndex = originalXml.indexOf(tagName)
        val startReplaceIndex = originalXml.indexOf(">", tagIndex) + 1
        val endReplaceIndex = originalXml.indexOf("</string>", startReplaceIndex)
        originalXml.replace(startReplaceIndex, endReplaceIndex, translatedText)

        val propMap: ArrayMap<String, Any> = ArrayMap()
        propMap[StatusKey.LOCALE] = "ru-en"
        propMap[StatusKey.INDEX] = iterationCount
        propMap[StatusKey.COUNT] = stringRowCount
        propMap[StatusKey.NAME] = name
        propMap[StatusKey.TEXT] = translatedText
        propMap[StatusKey.IS_SUCCESS] = true
        view?.updateTranslateStatus(propMap)

        checkFinish()
    }

    override fun onTranslateError(name: String, text: String) {
        iterationCount++

        val propMap: ArrayMap<String, Any> = ArrayMap()
        propMap[StatusKey.LOCALE] = "ru-en"
        propMap[StatusKey.INDEX] = iterationCount
        propMap[StatusKey.COUNT] = stringRowCount
        propMap[StatusKey.NAME] = name
        propMap[StatusKey.TEXT] = text
        propMap[StatusKey.IS_SUCCESS] = false
        view?.updateTranslateStatus(propMap)

        checkFinish()
    }

    private fun checkFinish() {
        if (iterationCount >= stringRowCount) {
            saveXmlFile()
        }
    }

    private fun saveXmlFile() {
        File(filePathWithoutName + "string-" + currentTranslateLocale + ".xml")
                .writeText(originalXml.toString())
    }
}