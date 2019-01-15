package com.di.penopllast.xmltranslater.data.repository.impl

import android.content.Context
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences

class RepositoryPreferenceImpl(context: Context) : RepositoryPreference {

    private val preferences: Preferences = BinaryPreferencesBuilder(context).build()
}