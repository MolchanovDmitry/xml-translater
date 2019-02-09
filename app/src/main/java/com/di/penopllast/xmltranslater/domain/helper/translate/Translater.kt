package com.di.penopllast.xmltranslater.domain.helper.translate

interface Translater {
    fun translate(apiKey: String, text: String, fromTo: String): String
}