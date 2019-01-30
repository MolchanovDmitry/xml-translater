package com.di.penopllast.xmltranslater.data.repository

interface RepositoryPreference {
    fun setFileLocale(locale: String)
    fun getFileLocale(): String
    fun setFilePath(path: String)
    fun getFilePath(): String
    fun setApiKey(key: String)
    fun getApiKey(): String
}