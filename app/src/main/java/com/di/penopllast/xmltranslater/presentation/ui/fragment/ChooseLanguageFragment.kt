package com.di.penopllast.xmltranslater.presentation.ui.fragment

import com.google.gson.internal.LinkedTreeMap

interface ChooseLanguageFragment {
    fun fillRecycler(langs: LinkedTreeMap<String, String>)
}