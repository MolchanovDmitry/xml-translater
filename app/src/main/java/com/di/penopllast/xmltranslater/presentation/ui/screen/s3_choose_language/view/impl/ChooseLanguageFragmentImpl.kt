package com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.impl

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
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.adapter.LanguageAdapter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter.ChooseLanguagePresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.ChooseLanguageFragment
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.SelectLanguageCallback
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, SelectLanguageCallback {

    private val presenter: ChooseLanguagePresenter = ChooseLanguagePresenterImpl(this)
    private val connector: ChooseLanguageConnector by lazy { context as ChooseLanguageConnector }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connector?.onResumeFragment(FragmentName.CHOOSE_LANGUAGE)
        presenter.getLangList()
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