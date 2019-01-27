package com.di.penopllast.xmltranslater.domain.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * locale - description
 */
@Entity
data class LocaleDescription(
        @field:PrimaryKey val locale: String,
        val description: String
)
