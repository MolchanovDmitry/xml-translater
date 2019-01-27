package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import android.util.ArrayMap
import androidx.room.Room
import com.di.penopllast.xmltranslater.data.repository.RepositoryDb
import com.di.penopllast.xmltranslater.domain.room.AppDatabase
import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.google.gson.internal.LinkedTreeMap

class RepositoryDbImpl(context: Context) : RepositoryDb {

    private val dao: DbDao = Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build().dbDao

    override fun getLocaleDescriptions(): List<LocaleDescription> {
        return dao.localeDescriptionList
    }

    override fun insertLocaleDescriptions(localeMap: ArrayMap<String, String>) {
        for (map in localeMap) {
            dao.insertLocaleDescription(LocaleDescription(map.key, map.value))
        }
    }

    override fun deleteLocaleDescriptions() {
        dao.clearLocaleDescription()
    }

    override fun getLocaleMatches(): List<LocaleMatch> {
        return dao.localeMatchList
    }

    override fun insertLocaleMatches(localeMap: LinkedTreeMap<String, String>) {
        for (map in localeMap) {
            dao.insertLocaleMatch(LocaleMatch(map.key, map.value))
        }
    }

    override fun deleteLocaleMatches() {
        dao.clearLocaleMatch()
    }

}