package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import com.di.penopllast.xmltranslater.domain.model.FileType
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences

class RepositoryPreferenceImpl(context: Context) : RepositoryPreference {

    companion object {
        private const val USER_LOCALE = "user_locale"
        private const val FILE_LOCALE = "file_locale"
        private const val LOCALE_DEFAULT = "en"
        private const val PATH = "path"
        private const val API_KEY = "api_key"
        private const val FILE_TYPE = "file_type"
    }

    private val preferences: Preferences = BinaryPreferencesBuilder(context).build()

    override fun setUserLocale(locale: String) {
        preferences.edit().putString(USER_LOCALE, locale).apply()
    }

    override fun getUserLocale(): String {
        return preferences.getString(USER_LOCALE, LOCALE_DEFAULT) ?: LOCALE_DEFAULT
    }

    override fun setFileLocale(locale: String) {
        preferences.edit().putString(FILE_LOCALE, locale).apply()
    }

    override fun getFileLocale(): String {
        return preferences.getString(FILE_LOCALE, LOCALE_DEFAULT) ?: LOCALE_DEFAULT
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

    override fun setFileType(@FileType fileType: Int) {
        preferences.edit().putInt(FILE_TYPE, fileType).apply()
    }

    override fun getFileType(): Int {
        return preferences.getInt(FILE_TYPE, -1)
    }
}