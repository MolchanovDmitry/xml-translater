package com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Intent
import android.net.Uri
import android.text.util.Linkify
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.util.LinkifyCompat
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.application.utils.Const
import com.di.penopllast.xmltranslater.presentation.controller.connector.SaveApiKeyConnector
import com.di.penopllast.xmltranslater.presentation.controller.lazy.bindView
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter.SaveApiKeyPresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter.SaveApiKeyPresenterImpl


class SaveApiKeyFragmentImpl : Fragment(), SaveApiKeyFragment {

    private val apiKeyEdit: EditText? by bindView(R.id.edit_view)
    private val pasteButton: Button? by bindView(R.id.button_paste)
    private val bottomText: TextView? by bindView(R.id.text_bottom_text)
    private val saveButton: Button? by bindView(R.id.button_save)
    private val cutButton: Button? by bindView(R.id.button_cut)

    private val presenter: SaveApiKeyPresenter = SaveApiKeyPresenterImpl(this)
    private val connector: SaveApiKeyConnector? by lazy { context as SaveApiKeyConnector }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_api_key, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkApiKeyExist()

        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        apiKeyEdit?.setText(Const.API_KEY)
        pasteButton?.setOnClickListener {
            val textToPaste = clipboard?.primaryClip?.getItemAt(0)?.text
            textToPaste?.let {
                apiKeyEdit?.setText(it)
            }
        }
        bottomText?.setOnClickListener {
            val uri = Uri.parse(getString(R.string.yandex_api_link))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        saveButton?.setOnClickListener { presenter.saveApiKey(apiKeyEdit?.text.toString()) }
        cutButton?.setOnClickListener { apiKeyEdit?.text?.clear() }
        bottomText?.let { LinkifyCompat.addLinks(it, Linkify.WEB_URLS) }
    }

    override fun updateApiKey(savedKey: String) {
        apiKeyEdit?.setText(savedKey)
    }

    override fun onFinish() {
        connector?.onSaveApiKey()
    }
}