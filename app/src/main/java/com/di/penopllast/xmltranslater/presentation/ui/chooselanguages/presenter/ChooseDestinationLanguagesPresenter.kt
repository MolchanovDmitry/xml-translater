package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter

interface ChooseDestinationLanguagesPresenter {
    fun getLocaleMatches()
    fun saveSelectedLocales(localeList: List<String>)
}