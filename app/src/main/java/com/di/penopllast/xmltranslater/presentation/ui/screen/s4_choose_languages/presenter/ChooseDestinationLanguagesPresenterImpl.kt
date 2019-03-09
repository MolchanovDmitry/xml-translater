package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.model.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.ChooseDestinationLanguagesFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChooseDestinationLanguagesPresenterImpl(
        private val view: ChooseDestinationLanguagesFragment?)
    : BasePresenter(), ChooseDestinationLanguagesPresenter {

    override fun getLocaleMatches() {
        scopeIO.launch {
            val fileLocale = repositoryPreference.getFileLocale()
            val localeDescriptions = repositoryDb.getLocaleDescriptions()
            val localeMathList = repositoryDb.getLocaleMatches()

            val extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch> = ArrayList()
            for (match in localeMathList) {
                if (match.localeFrom == fileLocale) {
                    extendedLocaleMatchList.add(ExtendedLocaleMatch(
                            match.localeFrom,
                            match.localeTo,
                            localeDescriptions[match.localeTo] ?: ""
                    ))
                }
            }
            withContext(Dispatchers.Main) { view?.showExtendedLocaleMatchList(extendedLocaleMatchList) }
        }
    }

    override fun saveSelectedLocales(localeList: List<String>) {
        scopeIO.launch {
            repositoryDb.deleteSelectedLanguages()
            repositoryDb.insertSelectedLocales(localeList)
            withContext(Dispatchers.Main) { view?.toTranslateFragment() }
        }
    }
}