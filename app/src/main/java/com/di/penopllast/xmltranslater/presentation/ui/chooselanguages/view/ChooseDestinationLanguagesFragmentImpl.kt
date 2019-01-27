package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.main.connector.ChooseLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.adapter.MatchLanguagesAdapter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseDestinationLanguagesFragmentImpl : Fragment(),
        ChooseDestinationLanguagesFragment, ChooseLanguagesConnector {

    private lateinit var presenter: ChooseDestinationLanguagesPresenter
    private val handler = Handler()
    private val selectedLocaleList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = ChooseDestinationLanguagesPresenterImpl(this)
        presenter.getLocaleMatches()

        return inflater.inflate(R.layout.fragment_choose_translate_languages, container, false)
    }

    override fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>) {
        handler.post {
            recycler_language_list.layoutManager = LinearLayoutManager(context)
            recycler_language_list.adapter = MatchLanguagesAdapter(extendedLocaleMatchList, this)
        }
    }

    override fun onUnLanguageSelected(locale: String) {
        selectedLocaleList.add(locale)
    }

    override fun onLanguageSelected(locale: String) {
        selectedLocaleList.remove(locale)
    }
}