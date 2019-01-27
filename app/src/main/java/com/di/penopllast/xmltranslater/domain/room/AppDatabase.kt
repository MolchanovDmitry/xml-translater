package com.di.penopllast.xmltranslater.domain.room

import com.di.penopllast.xmltranslater.domain.room.dao.DbDao
import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription

import androidx.room.Database
import androidx.room.RoomDatabase
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.di.penopllast.xmltranslater.domain.room.model.SelectedLocale

@Database(
        entities = [LocaleDescription::class, LocaleMatch::class, SelectedLocale::class],
        version = 5,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dbDao: DbDao
}
