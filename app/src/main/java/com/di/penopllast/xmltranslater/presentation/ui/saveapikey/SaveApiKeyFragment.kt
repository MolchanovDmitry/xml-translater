package com.di.penopllast.xmltranslater.presentation.ui.saveapikey

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.R
import kotlinx.android.synthetic.main.fragment_save_api_key.*

class SaveApiKeyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
    }
}