package com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.adapter.LogAdapter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.model.LogColor
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.presenter.TranslatePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.presenter.TranslatePresenterImpl
import android.net.Uri
import com.di.penopllast.xmltranslater.R
import java.io.File
import android.os.StrictMode
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.presentation.controller.lazy.bindView

class TranslateFragmentImpl : Fragment(), TranslateFragment {

    private val logRecyclerView: RecyclerView? by bindView(R.id.log_recycler_view)
    private val statusText: TextView? by bindView(R.id.status_text)

    private val presenter: TranslatePresenter = TranslatePresenterImpl(this)
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.generalTranslate()
        val layout = LinearLayoutManager(context)
        layout.reverseLayout = true
        logRecyclerView?.layoutManager = layout
        logRecyclerView?.adapter = LogAdapter()
    }

    override fun updateTranslateStatus(locale: String, index: Int, count: Int) {
        val status = "Locale: $locale: generalTranslate $index/$count"
        handler.post { statusText?.text = status }
    }

    override fun addUiLog(message: String, @LogColor color: Int) {
        handler.post { (logRecyclerView?.adapter as LogAdapter).addItem(message, getColor(color)) }
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

    override fun onEndTranslate(resultFilePathList: ArrayList<String>) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.putExtra(Intent.EXTRA_SUBJECT, "Translated files")
        intent.type = "*/*"

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val files = ArrayList<Uri>()

        resultFilePathList.forEach { path ->
            val file = File(path)
            val uri = Uri.fromFile(file)
            files.add(uri)
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)
        startActivity(intent)
    }
}