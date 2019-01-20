package com.di.penopllast.xmltranslater.presentation.ui

import androidx.annotation.StringDef

@StringDef
annotation class StatusKey {
    companion object {
        const val LOCALE = "locale"
        const val INDEX = "index"
        const val COUNT = "count"
        const val NAME = "name"
        const val TEXT = "text"
        const val IS_SUCCESS = "is_success"
    }
}
