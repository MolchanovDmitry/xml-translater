package com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter

import android.util.ArrayMap
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.domain.model.lang.RootLangs
import com.di.penopllast.xmltranslater.domain.room.model.LocaleMatch
import com.di.penopllast.xmltranslater.presentation.controller.presenter.BasePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.ChooseLanguageFragment
import kotlinx.coroutines.launch

class ChooseLanguagePresenterImpl(private val view: ChooseLanguageFragment?)
    : BasePresenter(), ChooseLanguagePresenter, ChooseLanguagePresenter.DownloadCallback {

    override fun getLangList() {
        scopeIO.launch {
            val locale = repositoryPreference.getUserLocale()
            repositoryNetwork.getLangList(locale, Const.API_KEY,
                    this@ChooseLanguagePresenterImpl)
        }
    }

    override fun onLanguageListFetched(rootLangs: RootLangs) {
        saveLocaleEntities(rootLangs)
        showLanguageList(rootLangs)
    }

    private fun saveLocaleEntities(rootLangs: RootLangs) {
        scopeIO.launch {

            val localeMatch: ArrayList<LocaleMatch> = ArrayList()

            rootLangs.dirs.forEachIndexed { index, dir ->
                val colonIndex = dir.indexOf('-')
                val fromLang = dir.substring(0, colonIndex)
                val toLang = dir.substring(colonIndex + 1)
                localeMatch.add(LocaleMatch(index, fromLang, toLang))
            }

            repositoryDb.deleteLocaleDescriptions()
            repositoryDb.deleteLocaleMatches()
            repositoryDb.insertLocaleDescriptions(rootLangs.langs)
            repositoryDb.insertLocaleMatches(localeMatch)
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

    override fun saveSelectedLocale(locale: String) {
        repositoryPreference.setFileLocale(locale)
    }

    override fun onLoadError() {
        view?.onLoadError()
    }

}