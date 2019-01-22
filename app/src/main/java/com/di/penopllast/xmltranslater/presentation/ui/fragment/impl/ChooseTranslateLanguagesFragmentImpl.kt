package com.di.penopllast.xmltranslater.presentation.ui.fragment.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.adapter.LanguagesAdapter
import kotlinx.android.synthetic.main.fragment_choose_language.*

class ChooseTranslateLanguagesFragmentImpl : Fragment() {

    private var fileLocale: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileLocale = arguments?.getString("locale")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_language, container, false)
    }

    fun fillLanguagesRecycler() {
        language_list_recycler.layoutManager = LinearLayoutManager(context)
        //language_list_recycler.adapter = LanguagesAdapter()
    }
}