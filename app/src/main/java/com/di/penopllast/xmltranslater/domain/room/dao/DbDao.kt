package com.di.penopllast.xmltranslater.domain.room.dao

import com.di.penopllast.xmltranslater.domain.room.model.LocaleMap

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {

    @get:Query("select * from LocaleMap")
    val localMapList: List<LocaleMap>

    @Query("delete from LocaleMap")
    fun clearLocaleMap()

    @Insert
    fun insert(starredApp: LocaleMap)

    @Delete
    fun delete(employee: LocaleMap)
}
