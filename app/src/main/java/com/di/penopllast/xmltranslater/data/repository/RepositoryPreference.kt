package com.di.penopllast.xmltranslater.data.repository

interface RepositoryPreference {
    fun setFileLocale(locale: String)
    fun getFileLocale(): String
}