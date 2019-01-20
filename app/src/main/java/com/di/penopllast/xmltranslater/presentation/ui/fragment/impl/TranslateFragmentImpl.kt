package com.di.penopllast.xmltranslater.presentation.ui.fragment.impl

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.fragment.TranslateFragment

class TranslateFragmentImpl : Fragment(), TranslateFragment {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onStart() {
        super.onStart()

    }

}