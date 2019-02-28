package com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_save_api_key.*
import android.content.Intent
import android.net.Uri
import android.text.util.Linkify
import androidx.core.text.util.LinkifyCompat
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.connector.SaveApiKeyConnector
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter.SaveApiKeyPresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.presenter.SaveApiKeyPresenterImpl


class SaveApiKeyFragmentImpl : Fragment(), SaveApiKeyFragment {

    private val presenter: SaveApiKeyPresenter = SaveApiKeyPresenterImpl(this)
    private val connector: SaveApiKeyConnector? by lazy { context as SaveApiKeyConnector }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_api_key, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connector?.onResumeFragment(FragmentName.SAVE_API_KEY)
        presenter.checkApiKeyExist()

        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        button_paste.setOnClickListener {
            val textToPaste = clipboard?.primaryClip?.getItemAt(0)?.text
            textToPaste?.let {
                edit_view.setText(it)
            }
        }
        text_bottom_text.setOnClickListener {
            val uri = Uri.parse(getString(R.string.yandex_api_link))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        button_save.setOnClickListener { presenter.saveApiKey(edit_view.text.toString()) }
        button_cut.setOnClickListener { edit_view.text.clear() }
        LinkifyCompat.addLinks(text_bottom_text, Linkify.WEB_URLS)
    }

    override fun updateApiKey(savedKey: String) {
        edit_view?.setText(savedKey)
    }

    override fun onFinish() {
        connector?.onSaveApiKey()
    }
}