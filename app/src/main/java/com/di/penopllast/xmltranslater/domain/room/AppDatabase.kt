package com.di.penopllast.xmltranslater.domain.room

import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription

import androidx.room.Database
import androidx.room.RoomDatabase
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch

@Database(
        entities = arrayOf(LocaleDescription::class, LocaleMatch::class),
        version = 3,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dbDao: DbDao
}
