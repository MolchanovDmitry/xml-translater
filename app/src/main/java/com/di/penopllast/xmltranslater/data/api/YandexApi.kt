package com.di.penopllast.xmltranslater.data.api

import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs
import com.di.penopllast.xmltranslater.domain.model.translate.Translate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApi {

    @GET("getLangList")
    fun getLanguageList(
            @Query("key") key: String,
            @Query("ui") ui: String
    ): Call<RootLangs>

    @GET("translate")
    fun translate(
            @Query("key") key: String,
            @Query("text") text: String?,
            @Query("lang") lang: String
    ): Call<Translate>
}