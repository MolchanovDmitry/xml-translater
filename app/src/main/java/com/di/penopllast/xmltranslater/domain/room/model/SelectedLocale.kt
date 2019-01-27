package com.di.penopllast.xmltranslater.domain.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SelectedLocale (
        @PrimaryKey val id: Int,
        val locale: String
)