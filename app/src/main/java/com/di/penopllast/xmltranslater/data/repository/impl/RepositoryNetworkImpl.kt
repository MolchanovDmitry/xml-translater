package com.di.penopllast.xmltranslater.data.repository.impl

import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.data.api.YandexApi
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import com.di.penopllast.xmltranslater.domain.model.RootLangs
import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryNetworkImpl : RepositoryNetwork {

    lateinit var yandexApi: YandexApi @Inject set

    init {
        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)
    }

    override fun getLangList(s: String, apiKey: String, callback: MainPresenter.DownloadLanguageCallback) {
        yandexApi.getLanguageList(apiKey, s).enqueue(object : Callback<RootLangs> {
            override fun onFailure(call: Call<RootLangs>, t: Throwable) {
                Utils.print("Error getting lang list " + t)
            }

            override fun onResponse(call: Call<RootLangs>, response: Response<RootLangs>) {
                response.body()?.langs?.let {
                    callback.onLanguageListFetched(it)
                }
            }

        })
    }

}