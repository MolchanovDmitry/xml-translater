package com.di.penopllast.xmltranslater.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.MainActivity
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseFileConnector
import kotlinx.android.synthetic.main.fragment_choose_file.*

class ChooseFileFragment : Fragment() {

    private lateinit var connector: ChooseFileConnector

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChooseFileConnector) {
            connector = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_file, container, false)
    }

    override fun onStart() {
        super.onStart()
        choose_file_button.setOnClickListener { showFileChooser() }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*xml*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    MainActivity.FILE_SELECT_CODE)
        } catch (ex: android.content.ActivityNotFoundException) {
            connector.showToast(getString(R.string.install_file_manager))
        }
    }
}