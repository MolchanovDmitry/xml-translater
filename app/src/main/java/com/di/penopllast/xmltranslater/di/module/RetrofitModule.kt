package com.di.penopllast.xmltranslater.di.module

import android.annotation.SuppressLint
import android.content.Context
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.X509Certificate
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.*

@Module
class RetrofitModule {

    @Provides
    @Named("serverUrl")
    internal fun provideServerUrl(): String {
        return "https://generalTranslate.yandex.net/api/v1.5/tr.json/"
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(@Named("serverUrl") serverUrl: String, builder: Retrofit.Builder,
                                 okHttpClient: OkHttpClient?): Retrofit {
        return builder
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(converterFactory: Converter.Factory): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(converterFactory)
    }

    @Provides
    @Singleton
    internal fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory
                .create(gson)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @Provides
    @Singleton
    internal fun provideHttpCache(context: Context): Cache {
        return Cache(File(context.cacheDir,
                "apiResponses"), (5 * 1024 * 1024).toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, context: Context): OkHttpClient? {
        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        var builder: OkHttpClient.Builder? = null
        try {
            trustManager = trustManagerForCertificates()
            val sslContext = SSLContext.getInstance(PROTOCOL)

            val trustAllCerts = arrayOf<TrustManager>(trustManager)
            sslContext.init(null, trustAllCerts, null)
            sslSocketFactory = sslContext.socketFactory

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            builder = OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .addInterceptor(logging)
                    .hostnameVerifier { s, sslSession -> true }


        } catch (e: GeneralSecurityException) {
            Utils.print("GeneralSecurityException $e")
        }

        return if (builder != null) builder.build() else null
    }

    companion object {

        private val PROTOCOL = "TLSv1.2"

        @Throws(GeneralSecurityException::class)
        private fun trustManagerForCertificates(): X509TrustManager {
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(null as KeyStore?)

            var x509Tm: X509TrustManager? = null
            for (tm in tmf.trustManagers) {
                if (tm is X509TrustManager) {
                    x509Tm = tm
                    break
                }
            }

            val finalTm = x509Tm
            return object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return if (finalTm != null) finalTm.acceptedIssuers else arrayOfNulls(0)
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
                }
            }
        }
    }

}