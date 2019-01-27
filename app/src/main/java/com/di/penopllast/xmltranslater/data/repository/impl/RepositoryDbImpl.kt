package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import android.util.ArrayMap
import androidx.room.Room
import com.di.penopllast.xmltranslater.data.repository.RepositoryDb
import com.di.penopllast.xmltranslater.domain.room.AppDatabase
import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.di.penopllast.xmltranslater.domain.room.model.SelectedLocale
import com.google.gson.internal.LinkedTreeMap

class RepositoryDbImpl(context: Context) : RepositoryDb {

    private val dao: DbDao = Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build().dbDao

    override fun getLocaleDescriptions(): ArrayMap<String, String> {
        val map: ArrayMap<String, String> = ArrayMap()
        dao.localeDescriptionList.forEach {
            map[it.locale] = it.description
        }
        return map
    }

    override fun insertLocaleDescriptions(localeMap: LinkedTreeMap<String, String>) {
        localeMap.forEach {
            dao.insertLocaleDescription(LocaleDescription(it.key, it.value))
        }
    }

    override fun deleteLocaleDescriptions() {
        dao.clearLocaleDescription()
    }

    override fun getLocaleMatches(): List<LocaleMatch> {
        return dao.localeMatchList
    }

    override fun insertLocaleMatches(localeMatches: List<LocaleMatch>) {
        localeMatches.forEach { dao.insertLocaleMatch(it) }
    }

    override fun deleteLocaleMatches() {
        dao.clearLocaleMatch()
    }

    override fun getSelectedLocales(): List<String> {
        val localeList = ArrayList<String>()
        dao.selectedLocaleList.forEach { localeList.add(it.locale) }
        return localeList
    }

    override fun insertSelectedLocales(localeList: List<String>) {
        localeList.forEachIndexed { index, s ->
            dao.insertSelectedLocale(SelectedLocale(index, s))
        }
    }

    override fun deleteSelectedLanguages() {
        dao.clearSelectedLocale()
    }
}