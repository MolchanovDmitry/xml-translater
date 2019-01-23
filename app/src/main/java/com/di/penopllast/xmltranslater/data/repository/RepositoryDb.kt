package com.di.penopllast.xmltranslater.data.repository

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMap

interface RepositoryDb {
    fun getLocaleMaps(): List<LocaleMap>
    fun insertLocaleMaps(localeMap: ArrayMap<String, String>)
    fun deleteLocaleMaps()
}