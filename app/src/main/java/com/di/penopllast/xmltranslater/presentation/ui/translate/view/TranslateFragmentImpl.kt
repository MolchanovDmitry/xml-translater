package com.di.penopllast.xmltranslater.presentation.ui.translate.view

import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.StatusKey
import com.di.penopllast.xmltranslater.presentation.ui.translate.adapter.LogAdapter
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragmentImpl : Fragment(), TranslateFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onStart() {
        super.onStart()
        val layout = LinearLayoutManager(context)
        layout.reverseLayout = true
        log_recycler_view.layoutManager = layout
        log_recycler_view.adapter = LogAdapter()
    }

    override fun updateFragmentTranslateStatus(propMap: ArrayMap<String, Any>) {
        val locale = propMap[StatusKey.LOCALE]
        val index = propMap[StatusKey.INDEX]
        val count = propMap[StatusKey.COUNT]
        val name = propMap[StatusKey.NAME]
        val text = propMap[StatusKey.TEXT]
        val isSuccess = propMap[StatusKey.IS_SUCCESS]

        val status = "Locale: $locale: translate $index/$count"
        status_text.text = status

        var message = if (isSuccess as Boolean) "Success translate: " else "Fail translate"
        message += " name = $name text = $text"

        (log_recycler_view.adapter as LogAdapter).addItem(message)
    }
}