package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.presenter.connector.ChooseLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.presenter.connector.FinishChooseDestinationLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.adapter.MatchLanguagesAdapter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_translate_languages.*

class ChooseDestinationLanguagesFragmentImpl : Fragment(),
        ChooseDestinationLanguagesFragment, ChooseLanguagesConnector {

    private lateinit var presenter: ChooseDestinationLanguagesPresenter
    private var connector: FinishChooseDestinationLanguagesConnector? = null
    private val handler = Handler()
    private val selectedLocaleList = ArrayList<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connector = if (context is FinishChooseDestinationLanguagesConnector) context else null
    }

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

    override fun onStart() {
        super.onStart()
        button_translate.setOnClickListener { presenter.saveSelectedLocales(selectedLocaleList) }

    }

    override fun onUnLanguageSelected(locale: String) {
        selectedLocaleList.add(locale)
    }

    override fun onLanguageSelected(locale: String) {
        selectedLocaleList.remove(locale)
    }

    override fun toTranslateFragment() {
        handler.post {
            connector?.onFinishChooseDestinationLanguages()
        }
    }
}