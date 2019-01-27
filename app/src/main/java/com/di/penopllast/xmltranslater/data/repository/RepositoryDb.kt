package com.di.penopllast.xmltranslater.data.repository

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.google.gson.internal.LinkedTreeMap

interface RepositoryDb {
    fun getLocaleDescriptions(): List<LocaleDescription>
    fun insertLocaleDescriptions(localeMap: ArrayMap<String, String>)
    fun deleteLocaleDescriptions()
    fun getLocaleMatches(): List<LocaleMatch>
    fun insertLocaleMatches(localeMap: LinkedTreeMap<String, String>)
    fun deleteLocaleMatches()
}