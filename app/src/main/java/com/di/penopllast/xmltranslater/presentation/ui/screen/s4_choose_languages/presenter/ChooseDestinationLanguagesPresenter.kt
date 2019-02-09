package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.presenter

interface ChooseDestinationLanguagesPresenter {
    fun getLocaleMatches()
    fun saveSelectedLocales(localeList: List<String>)
}