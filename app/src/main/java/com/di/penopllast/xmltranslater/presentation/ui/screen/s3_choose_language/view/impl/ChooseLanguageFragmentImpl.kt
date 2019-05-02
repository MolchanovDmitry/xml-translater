package com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.impl

import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.controller.lazy.bindView
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.adapter.LanguageAdapter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter.ChooseLanguagePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.presenter.ChooseLanguagePresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.ChooseLanguageFragment
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.SelectLanguageCallback

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, SelectLanguageCallback {

    private val recyclerView: RecyclerView? by bindView(R.id.recycler_language_list)
    private val progressBar: ProgressBar? by bindView(R.id.progress_bar)

    private val presenter: ChooseLanguagePresenter = ChooseLanguagePresenterImpl(this)
    private val connector: ChooseLanguageConnector? by lazy { context as ChooseLanguageConnector }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getLangList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun showLanguageList(langMap: ArrayMap<String, String>) {
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = LanguageAdapter(langMap, this)
        progressBar?.visibility = View.GONE
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