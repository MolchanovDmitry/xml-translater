package com.di.penopllast.xmltranslater.presentation.ui.s1_save_api_key.view

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
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.presenter.connector.SaveApiKeyConnector
import com.di.penopllast.xmltranslater.presentation.ui.s1_save_api_key.presenter.SaveApiKeyPresenter
import com.di.penopllast.xmltranslater.presentation.ui.s1_save_api_key.presenter.SaveApiKeyPresenterImpl


class SaveApiKeyFragmentImpl : Fragment(), SaveApiKeyFragment {

    private lateinit var presenter: SaveApiKeyPresenter
    private var connector: SaveApiKeyConnector? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connector = context as SaveApiKeyConnector
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter = SaveApiKeyPresenterImpl(this)
        return inflater.inflate(R.layout.fragment_save_api_key, container, false)
    }

    override fun onStart() {
        super.onStart()

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
        button_save.setOnClickListener {

        }
    }

    override fun onFinish() {
        connector?.onSaveApiKey()
    }
}