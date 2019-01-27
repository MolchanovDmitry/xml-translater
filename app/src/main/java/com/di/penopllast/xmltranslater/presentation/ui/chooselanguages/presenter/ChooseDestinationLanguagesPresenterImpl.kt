package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter

import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view.ChooseDestinationLanguagesFragment

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