package com.di.penopllast.xmltranslater.presentation.ui.s5_translate.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.adapter.LogAdapter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.model.LogColor
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter.TranslatePresenter
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.presenter.TranslatePresenterImpl
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragmentImpl : Fragment(), TranslateFragment {

    private lateinit var presenter: TranslatePresenter
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TranslatePresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.generalTranslate()
        val layout = LinearLayoutManager(context)
        layout.reverseLayout = true
        log_recycler_view.layoutManager = layout
        log_recycler_view.adapter = LogAdapter()
    }

    override fun updateTranslateStatus(locale: String, index: Int, count: Int) {
        val status = "Locale: $locale: generalTranslate $index/$count"
        handler.post { status_text.text = status }
    }

    override fun addUiLog(message: String, @LogColor color: Int) {
        handler.post { (log_recycler_view.adapter as LogAdapter).addItem(message, getColor(color)) }
    }

    private fun getColor(@LogColor color: Int): Int = when (color) {
        LogColor.RED -> Color.RED
        LogColor.GREEN -> Color.GREEN
        LogColor.YELLOW -> Color.YELLOW
        else -> Color.WHITE
    }

    override fun showToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}