package com.di.penopllast.xmltranslater.application.utils

import android.util.Log

object Utils {
    private const val TAG = "DisLiker"

    init {
        throw AssertionError()
    }

    fun print(message: String) {
        Log.i(TAG, message)
    }
}
