package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.presenter

import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.model.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.ChooseDestinationLanguagesFragment

class ChooseDestinationLanguagesPresenterImpl(
        private val view: ChooseDestinationLanguagesFragment? = null)
    : BasePresenter(), ChooseDestinationLanguagesPresenter {

    override fun getLocaleMatches() {
        executorService.execute {
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
            view?.showExtendedLocaleMatchList(extendedLocaleMatchList)
        }
    }

    override fun saveSelectedLocales(localeList: List<String>) {
        executorService.execute {
            repositoryDb.deleteSelectedLanguages()
            repositoryDb.insertSelectedLocales(localeList)
            view?.toTranslateFragment()
        }
    }
}