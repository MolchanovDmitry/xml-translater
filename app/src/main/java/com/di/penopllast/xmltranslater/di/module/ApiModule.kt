package com.di.penopllast.xmltranslater.di.module

import com.di.penopllast.xmltranslater.data.api.YandexApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = arrayOf(RetrofitModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): YandexApi {
        return retrofit.create(YandexApi::class.java)
    }

}