package com.di.penopllast.xmltranslater.application.utils

import android.util.Log

class Utils private constructor() {

    init {
        throw AssertionError()
    }

    companion object {
        private const val TAG = "Translater"

        fun print(message: String) {
            Log.i(TAG, message)
        }
    }
}
