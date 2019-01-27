package com.di.penopllast.xmltranslater.data.repository

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.google.gson.internal.LinkedTreeMap

interface RepositoryDb {
    fun getLocaleDescriptions(): ArrayMap<String, String>
    fun insertLocaleDescriptions(localeMap: LinkedTreeMap<String, String>)
    fun deleteLocaleDescriptions()
    fun getLocaleMatches(): List<LocaleMatch>
    fun insertLocaleMatches(localeMatches: List<LocaleMatch>)
    fun deleteLocaleMatches()
    fun getSelectedLocales(): List<String>
    fun insertSelectedLocales(localeList: List<String>)
    fun deleteSelectedLanguages()
}