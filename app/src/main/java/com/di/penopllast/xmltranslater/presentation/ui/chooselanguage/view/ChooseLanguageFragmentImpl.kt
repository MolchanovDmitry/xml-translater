package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view

import android.os.Bundle
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
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, ChooseLanguageConnector {

    lateinit var presenter: ChooseLanguagePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = ChooseLanguagePresenterImpl(this)
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun showLanguageList(langs: LinkedTreeMap<String, String>) {
        language_list_recycler.layoutManager = LinearLayoutManager(context)
        language_list_recycler.adapter = LanguageAdapter(langs, this)
    }

    override fun onLanguageSelected(locale: String) {
    }
}