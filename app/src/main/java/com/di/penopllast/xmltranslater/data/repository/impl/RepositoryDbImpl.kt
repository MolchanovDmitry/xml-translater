package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import android.util.ArrayMap
import androidx.room.Room
import com.di.penopllast.xmltranslater.data.repository.RepositoryDb
import com.di.penopllast.xmltranslater.domain.room.AppDatabase
import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMap

class RepositoryDbImpl(context: Context) : RepositoryDb {

    private val dao: DbDao = Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build().dbDao

    override fun getLocaleMaps(): List<LocaleMap> {
        return dao.localMapList
    }

    override fun insertLocaleMaps(localeMap: ArrayMap<String, String>) {
        for (map in localeMap) {
            dao.insert(LocaleMap(map.key, map.value))
        }
    }

    override fun deleteLocaleMaps() {
        dao.clearLocaleMap()
    }
}