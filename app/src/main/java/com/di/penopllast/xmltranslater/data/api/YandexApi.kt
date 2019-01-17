package com.di.penopllast.xmltranslater.data.api

import com.di.penopllast.xmltranslater.domain.model.RootLangs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApi {

    @GET("getLangs")
    fun getLanguageList(
            @Query("key") key: String,
            @Query("ui") ui: String
    ): Call<RootLangs>

    @GET("translate")
    fun translate(
            @Query("key") key: String,
            @Query("text") text: String,
            @Query("lang") lang: String
    )
}