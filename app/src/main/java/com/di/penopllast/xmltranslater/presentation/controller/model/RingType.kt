package com.di.penopllast.xmltranslater.presentation.controller.model

import androidx.annotation.IntDef

@IntDef
annotation class RingType {
    companion object {
        const val RING_0_API_KEY = 0
        const val RING_1_FILE = 1
        const val RING_2_FROM_LOCALE = 2
        const val RING_3_TO_LOCALE = 3
        const val RING_4_TRANSLATE = 4
    }
}