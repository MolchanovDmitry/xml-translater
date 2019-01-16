package com.di.penopllast.xmltranslater.presentation.ui.fragment.impl

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.ui.adapter.LanguageAdapter
import com.di.penopllast.xmltranslater.presentation.ui.fragment.ChooseLanguageFragment
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseLanguageFragmentImpl : Fragment(), ChooseLanguageFragment, ChooseLanguageConnector {

    var connector: ChooseLanguageConnector? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connector = if (context is ChooseLanguageConnector) context else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    override fun fillRecycler(langs: LinkedTreeMap<String, String>) {
        language_list_recycler.layoutManager = LinearLayoutManager(context)
        language_list_recycler.adapter = LanguageAdapter(langs, this)
    }

    override fun onLanguageSelected(locale: String) {
        connector?.onLanguageSelected(locale)
    }

}