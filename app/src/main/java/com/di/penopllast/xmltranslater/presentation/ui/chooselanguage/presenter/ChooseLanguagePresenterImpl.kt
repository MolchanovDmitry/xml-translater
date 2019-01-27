package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs
import com.di.penopllast.xmltranslater.presentation.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view.ChooseLanguageFragment

class ChooseLanguagePresenterImpl(private val view: ChooseLanguageFragment? = null)
    : BasePresenter(), ChooseLanguagePresenter, ChooseLanguagePresenter.DownloadCallback {

    private val currentLocale = "ru"

    override fun getLangList() {
        repositoryNetwork.getLangList(currentLocale, Const.API_KEY, this)
    }

    override fun onLanguageListFetched(rootLangs: RootLangs) {
        saveLocaleEntities(rootLangs)
        showLanguageList(rootLangs)


    }

    private fun saveLocaleEntities(rootLangs: RootLangs) {
        executorService.submit {

            val localeMath: ArrayMap<String, String> = ArrayMap()

            for (dir in rootLangs.dirs) {
                val colonIndex = dir.indexOf('-')
                val fromLang = dir.substring(0, colonIndex)
                localeMath[fromLang] = dir.substring(colonIndex + 1)
            }
            repositoryDb.deleteLocaleDescriptions()
            repositoryDb.deleteLocaleMatches()
            repositoryDb.insertLocaleDescriptions(localeMath)
            repositoryDb.insertLocaleMatches(rootLangs.langs)
        }
    }

    private fun showLanguageList(rootLangs: RootLangs) {

        val langMap: ArrayMap<String, String> = ArrayMap() // locale - description

        for (dir in rootLangs.dirs) {
            val colonIndex = dir.indexOf('-')
            val fromLang = dir.substring(0, colonIndex)
            langMap[fromLang] = rootLangs.langs[fromLang]
        }

        view?.showLanguageList(langMap)
    }
}