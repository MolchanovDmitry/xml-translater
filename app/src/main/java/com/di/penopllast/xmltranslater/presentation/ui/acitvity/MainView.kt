package com.di.penopllast.xmltranslater.presentation.ui.acitvity

import android.util.ArrayMap
import com.google.gson.internal.LinkedTreeMap

interface MainView {
    fun onLanguageListFetched(langs: LinkedTreeMap<String, String>)
    fun updateTranslateStatus(propMap: ArrayMap<String, Any>)
}