package com.di.penopllast.xmltranslater.presentation.presenter

import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.MainView
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import org.json.JSONObject
import com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser.XmlToJson
import com.di.penopllast.xmltranslater.domain.model.string.StringRoot
import com.google.gson.Gson


class MainPresenterImpl(val view: MainView)
    : BasePresenter(), MainPresenter, MainPresenter.DownloadLanguageCallback {

    override fun getLangs() {
        repositoryNetwork.getLangList("ru", Const.API_KEY, this)
    }

    override fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        view.onLanguageListFetched(langs)
    }

    override fun parseXmlFile() {
        val result = readFileLineByLineUsingForEachLine("/sdcard/strings.xml")
        val xmlToJson = XmlToJson.Builder(result).build()
        val jsonObject = xmlToJson.toJson()
        val root: StringRoot = Gson().fromJson(jsonObject?.toString(), StringRoot::class.java)
    }

    fun readFileLineByLineUsingForEachLine(fileName: String): String =
            File(fileName).readText(Charsets.UTF_8)

}