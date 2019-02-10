package com.di.penopllast.xmltranslater.data.repository

import com.di.penopllast.xmltranslater.domain.model.FileType

interface RepositoryPreference {
    fun setFileLocale(locale: String)
    fun getFileLocale(): String
    fun setFilePath(path: String)
    fun getFilePath(): String
    fun setApiKey(key: String)
    fun getApiKey(): String
    fun setUserLocale(locale: String)
    fun getUserLocale(): String
    fun setFileType(@FileType fileType: Int)
    fun getFileType(): Int
}