package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter

import android.util.Log
import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view.ChooseDestinationLanguagesFragment

class ChooseDestinationLanguagesPresenterImpl(val view: ChooseDestinationLanguagesFragment? = null)
    : BasePresenter(), ChooseDestinationLanguagesPresenter {

    override fun getLocaleMatches(fileLocale: String) {
        executorService.execute {
            val localeDescriptions = repositoryDb.getLocaleDescriptions()
            val localeMathList = repositoryDb.getLocaleMatches()

            val extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch> = ArrayList()
            for (match in localeMathList) {
                Log.i("1488", "Compare ${match.localeFrom} with $fileLocale")
                if (match.localeFrom == fileLocale) {
                    Log.i("1488", "Compare successfull")
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
}