package com.di.penopllast.xmltranslater.domain.helper.translate

import androidx.annotation.IntDef

@IntDef
annotation class MessageType {
    companion object {
        const val DEFAULT = 0
        const val OK = 1
        const val NOTIFY = 2
        const val ERROR = 3
    }

}