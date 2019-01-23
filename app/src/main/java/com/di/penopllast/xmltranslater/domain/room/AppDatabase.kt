package com.di.penopllast.xmltranslater.domain.room

import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMap

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LocaleMap::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val dbDao: DbDao
}
