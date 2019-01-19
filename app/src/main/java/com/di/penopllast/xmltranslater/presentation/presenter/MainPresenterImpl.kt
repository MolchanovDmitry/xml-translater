package com.di.penopllast.xmltranslater.presentation.presenter

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.MainView
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser.XmlToJson
import com.di.penopllast.xmltranslater.domain.model.string.StringRoot
import com.di.penopllast.xmltranslater.domain.model.string.StringRow
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainPresenterImpl(val view: MainView)
    : MainPresenter,
        BasePresenter(),
        MainPresenter.DownloadLanguageCallback,
        MainPresenter.TranslateCallback {

    private val stringMap: ArrayMap<String, String> = ArrayMap()
    private var stringRowCount = 0

    override fun getLangs() {
        repositoryNetwork.getLangList("ru", Const.API_KEY, this)
    }

    override fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        view.onLanguageListFetched(langs)
    }

    private var stringRows: List<StringRow>? = null
    override fun translate() {
        GlobalScope.launch {
            stringRows = parseXmlFile()
            translateWords(stringRows)
        }
    }

    private fun parseXmlFile(): List<StringRow>? {
        val result = readFileLineByLineUsingForEachLine("/sdcard/strings.xml")
        val xmlToJson = XmlToJson.Builder(result).build()
        val jsonObject = xmlToJson.toJson()
        return Gson().fromJson(jsonObject?.toString(), StringRoot::class.java).resources?.string
    }

    var iterationCount: Int = 0
    private fun translateWords(stringRows: List<StringRow>?) {
        stringRows?.let {
            stringRowCount = it.size
            repositoryNetwork.translate(Const.API_KEY,
                    it.get(iterationCount).name,
                    it.get(iterationCount).content,
                    "ru-en", this)

        }
    }

    override fun onTranslated(key: String?, translatedValue: String) {
        iterationCount++
        translateWords(stringRows)
        /*stringMap.put(key, translatedValue)
        if (stringRowCount == stringMap.size) {
            view.onTranslateComplete()
        }*/
    }

    fun readFileLineByLineUsingForEachLine(fileName: String): String =
            File(fileName).readText(Charsets.UTF_8)

}