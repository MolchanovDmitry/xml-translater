package com.di.penopllast.xmltranslater.presentation.ui.fragment.impl

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.adapter.LogAdapter
import com.di.penopllast.xmltranslater.presentation.ui.fragment.TranslateFragment
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragmentImpl : Fragment(), TranslateFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onStart() {
        super.onStart()
        log_recycler_view.layoutManager = LinearLayoutManager(context)
        log_recycler_view.adapter = LogAdapter()
    }

    override fun setLog(key: String, text: String, success: Boolean) {
    }

}