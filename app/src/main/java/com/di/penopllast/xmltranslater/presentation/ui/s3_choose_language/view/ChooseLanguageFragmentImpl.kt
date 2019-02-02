package com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.view

import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.adapter.LanguageAdapter
import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.presenter.ChooseLanguagePresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, ChooseLanguageConnector {

    private lateinit var presenter: ChooseLanguagePresenter
    private var connector: ChooseLanguageConnector? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connector = context as ChooseLanguageConnector
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = ChooseLanguagePresenterImpl(this)
        presenter.getLangList()
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun showLanguageList(langMap: ArrayMap<String, String>) {
        recycler_language_list.layoutManager = LinearLayoutManager(context)
        recycler_language_list.adapter = LanguageAdapter(langMap, this)
    }

    override fun onLanguageSelected(locale: String) {
        presenter.saveSelectedLocale(locale)
        connector?.onLanguageSelected(locale)
    }

    override fun onLoadError() {
        Toast.makeText(context, "Error loadind language list. Check your interner connection"
                , Toast.LENGTH_SHORT).show()
    }
}