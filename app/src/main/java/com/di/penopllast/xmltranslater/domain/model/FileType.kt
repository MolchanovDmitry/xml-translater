package com.di.penopllast.xmltranslater.domain.model

import androidx.annotation.IntDef

@IntDef
annotation class FileType {
    companion object {
        const val XML = 0
        const val PHP = 1
    }
}