package com.di.penopllast.xmltranslater.domain.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * localeFrom - LocaleTo
 */
@Entity
data class LocaleMatch(
        @PrimaryKey val id: Int,
        val localeFrom: String,
        val localeTo: String
)