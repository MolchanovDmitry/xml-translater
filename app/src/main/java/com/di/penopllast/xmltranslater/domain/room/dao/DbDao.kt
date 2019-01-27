package com.di.penopllast.xmltranslater.domain.room.dao

import com.di.penopllast.xmltranslater.domain.room.model.LocaleDescription

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch

@Dao
interface DbDao {

    @get:Query("select * from LocaleDescription")
    val localeDescriptionList: List<LocaleDescription>

    @get:Query("select * from LocaleMatch")
    val localeMatchList: List<LocaleMatch>

    @Query("delete from LocaleDescription")
    fun clearLocaleDescription()

    @Query("delete from LocaleMatch")
    fun clearLocaleMatch()

    @Insert
    fun insertLocaleDescription(localeDescription: LocaleDescription)

    @Insert
    fun insertLocaleMatch(localeMatch: LocaleMatch)

    @Delete
    fun deleteLocaleDescription(localeDescription: LocaleDescription)

    @Delete
    fun deleteLocaleMatch(localeMatch: LocaleMatch)
}
