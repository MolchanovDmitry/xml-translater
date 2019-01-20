package com.di.penopllast.xmltranslater.presentation.presenter

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.MainView
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser.XmlToJson
import com.di.penopllast.xmltranslater.domain.model.string.StringRoot
import com.di.penopllast.xmltranslater.presentation.ui.StatusKey
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder


class MainPresenterImpl(val view: MainView)
    : MainPresenter,
        BasePresenter(),
        MainPresenter.DownloadLanguageCallback,
        MainPresenter.TranslateCallback {

    private lateinit var originalXml: StringBuilder
    private var stringRowCount = 0

    override fun getLangs() {
        repositoryNetwork.getLangList("ru", Const.API_KEY, this)
    }

    override fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        view.onLanguageListFetched(langs)
    }

    private var stringRoot: StringRoot? = null
    override fun translate() {
        GlobalScope.launch {
            stringRoot = parseXmlFile()
            translateWords()
        }
    }

    private fun parseXmlFile(): StringRoot? {
        val xml = File("/sdcard/strings.xml").readText(Charsets.UTF_8)
        originalXml = StringBuilder(xml)
        val xmlToJson = XmlToJson.Builder(xml).build()
        val jsonObject = xmlToJson.toJson()
        return Gson().fromJson(jsonObject?.toString(), StringRoot::class.java)
    }

    private var iterationCount: Int = 0
    private fun translateWords() {
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
                        "ru-en", this)
            }
        }
    }

    private fun saveXmlFile() {
        File("/sdcard/translated_strings.xml").writeText(originalXml.toString())
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
        view.updateTranslateStatus(propMap)

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
        view.updateTranslateStatus(propMap)

        checkFinish()
    }

    private fun checkFinish() {
        if (iterationCount >= stringRowCount) {
            saveXmlFile()
        }
    }

}