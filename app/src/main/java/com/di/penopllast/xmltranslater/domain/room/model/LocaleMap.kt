package com.di.penopllast.xmltranslater.domain.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocaleMap(
        @field:PrimaryKey val locale: String,
        val description: String
)
