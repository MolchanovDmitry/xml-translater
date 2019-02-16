package com.di.penopllast.xmltranslater.presentation.controller.connector

import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName

interface BaseConnector {
    fun onResumeFragment(@FragmentName fragmentName: String)
}