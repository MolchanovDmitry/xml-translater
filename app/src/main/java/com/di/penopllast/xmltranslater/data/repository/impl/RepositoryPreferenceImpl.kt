package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences

class RepositoryPreferenceImpl(context: Context) : RepositoryPreference {

    companion object {
        private const val LOCALE = "locale"
        private const val PATH = "path"
        private const val NO_LOCALE = "no_locale"
        private const val API_KEY = "api_key"
    }

    private val preferences: Preferences = BinaryPreferencesBuilder(context).build()

    override fun setFileLocale(locale: String) {
        preferences.edit().putString(LOCALE, locale).apply()
    }

    override fun getFileLocale(): String {
        return preferences.getString(LOCALE, NO_LOCALE) ?: NO_LOCALE
    }

    override fun setFilePath(path: String) {
        preferences.edit().putString(PATH, path).apply()
    }

    override fun getFilePath(): String {
        return preferences.getString(PATH, "") ?: ""
    }

    override fun setApiKey(key: String) {
        preferences.edit().putString(API_KEY, key).apply()
    }

    override fun getApiKey(): String {
        return preferences.getString(API_KEY, "") ?: ""
    }
}