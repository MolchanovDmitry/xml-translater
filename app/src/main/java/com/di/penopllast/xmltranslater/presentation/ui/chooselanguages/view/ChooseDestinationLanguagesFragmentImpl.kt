package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.adapter.MatchLanguagesAdapter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.presenter.ChooseDestinationLanguagesPresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseDestinationLanguagesFragmentImpl : Fragment(), ChooseDestinationLanguagesFragment, ChooseLanguagesConnector {

    private lateinit var presenter: ChooseDestinationLanguagesPresenter
    private val handler: Handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fileLocale = arguments?.getString("locale") ?: ""
        presenter = ChooseDestinationLanguagesPresenterImpl(this)
        presenter.getLocaleMatches(fileLocale)

        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun showExtendedLocaleMatchList(extendedLocaleMatchList: ArrayList<ExtendedLocaleMatch>) {
        handler.post {
            language_list_recycler.layoutManager = LinearLayoutManager(context)
            language_list_recycler.adapter = MatchLanguagesAdapter(extendedLocaleMatchList, this)
        }
    }

    override fun onUnLanguageSelected(locale: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLanguageSelected(locale: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}