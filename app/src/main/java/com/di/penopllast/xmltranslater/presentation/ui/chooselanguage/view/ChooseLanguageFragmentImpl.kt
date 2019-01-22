package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view

import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.adapter.LanguageAdapter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.presenter.ChooseLanguagePresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, ChooseLanguageConnector {

    private lateinit var presenter: ChooseLanguagePresenter
    private var connector: ChooseLanguageConnector? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connector = if (context is ChooseLanguageConnector) context else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = ChooseLanguagePresenterImpl(this)
        presenter.getLangList()
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun showLanguageList(langMap: ArrayMap<String, String>) {
        language_list_recycler.layoutManager = LinearLayoutManager(context)
        language_list_recycler.adapter = LanguageAdapter(langMap, this)
    }

    override fun onLanguageSelected(locale: String) {
        connector?.onLanguageSelected(locale)
    }
}