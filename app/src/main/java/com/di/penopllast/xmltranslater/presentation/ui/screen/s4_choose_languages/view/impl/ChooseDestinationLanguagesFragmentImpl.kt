package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.impl

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.connector.FinishChooseDestinationLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.adapter.MatchLanguagesAdapter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.model.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.presenter.ChooseDestinationLanguagesPresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.presenter.ChooseDestinationLanguagesPresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.ChooseDestinationLanguagesFragment
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.SelectLanguagesCallback
import kotlinx.android.synthetic.main.fragment_choose_translate_languages.*

class ChooseDestinationLanguagesFragmentImpl : Fragment(),
        ChooseDestinationLanguagesFragment, SelectLanguagesCallback {

    private val presenter: ChooseDestinationLanguagesPresenter = ChooseDestinationLanguagesPresenterImpl(this)
    private val connector: FinishChooseDestinationLanguagesConnector?
            by lazy { context as FinishChooseDestinationLanguagesConnector }
    private val handler = Handler()
    private val selectedLocaleList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_translate_languages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connector?.onResumeFragment(FragmentName.CHOOSE_TRANSLATION_LANGUAGE)
        presenter.getLocaleMatches()
    }

    override fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>) {
        handler.post {
            recycler_language_list.layoutManager = LinearLayoutManager(context)
            recycler_language_list.adapter = MatchLanguagesAdapter(extendedLocaleMatchList, this)
        }
    }

    override fun onStart() {
        super.onStart()
        button_translate.setOnClickListener { presenter.saveSelectedLocales(selectedLocaleList) }
    }

    override fun onUnLanguageSelected(locale: String) {
        selectedLocaleList.remove(locale)
    }

    override fun onLanguageSelected(locale: String) {
        if (!selectedLocaleList.contains(locale))
            selectedLocaleList.add(locale)
    }

    override fun toTranslateFragment() {
        handler.post {
            connector?.onFinishChooseDestinationLanguages()
        }
    }
}