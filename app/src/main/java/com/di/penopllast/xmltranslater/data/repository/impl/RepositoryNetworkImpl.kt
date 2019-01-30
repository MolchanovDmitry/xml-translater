package com.di.penopllast.xmltranslater.data.repository.impl

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.data.api.YandexApi
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs
import com.di.penopllast.xmltranslater.domain.model.translate.Translate
import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter.TranslatePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryNetworkImpl : RepositoryNetwork {

    lateinit var yandexApi: YandexApi @Inject set

    init {
        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)
    }

    override fun getLangList(s: String, apiKey: String,
                             callback: ChooseLanguagePresenter.DownloadCallback) {
        yandexApi.getLanguageList(apiKey, s).enqueue(object : Callback<RootLangs> {
            override fun onFailure(call: Call<RootLangs>, t: Throwable) {
                callback.onLoadError()
            }

            override fun onResponse(call: Call<RootLangs>, response: Response<RootLangs>) {
                response.body()?.let {
                    callback.onLanguageListFetched(it)
                }
            }
        })
    }

    override fun translate(apiKey: String, key: String, text: String, fromTo: String,
                           callback: TranslatePresenter.TranslateCallback) {

        val response: Response<Translate> = yandexApi.translate(apiKey, text, fromTo).execute()
        response.body()?.let {
            it.text?.let { textIt ->
                callback.onTranslated(key, textIt[0])
            } ?: callback.onTranslateError(key, text)
        } ?: callback.onTranslateError(key, text)

    }
}