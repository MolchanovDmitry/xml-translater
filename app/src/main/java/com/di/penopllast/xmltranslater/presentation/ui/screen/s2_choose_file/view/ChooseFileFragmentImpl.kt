package com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.controller.view.MainActivity
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter.ChooseFilePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter.ChooseFilePresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_file.*
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.IntDef
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.domain.model.FileType


class ChooseFileFragmentImpl : Fragment(), ChooseFileFragment, AdapterView.OnItemSelectedListener {

    private lateinit var connector: ChooseFileConnector
    private lateinit var presenter: ChooseFilePresenter
    private val handler = Handler()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChooseFileConnector) {
            connector = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ChooseFilePresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_file, container, false)
    }

    override fun onStart() {
        super.onStart()
        ArrayAdapter.createFromResource(
                activity,
                R.array.file_type,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_file_type.adapter = adapter
            spinner_file_type.onItemSelectedListener = this
        }
        choose_file_button.setOnClickListener { showFileChooser() }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        presenter.setFileType(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        text_hint.visibility = View.GONE
        button_submit.visibility = View.GONE
        presenter.checkFileExist()
    }

    override fun showSavedFile(filePath: String) {
        context?.let {

            val firstStringPath = getString(R.string.previous_file)
            val text = firstStringPath + filePath

            val spannable = SpannableString(text)
            spannable.setSpan(ForegroundColorSpan(it.getColor(R.color.orange)),
                    firstStringPath.length, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            handler.post {
                text_hint.text = spannable

                text_hint.visibility = View.VISIBLE

                button_submit.visibility = View.VISIBLE
                button_submit.setOnClickListener {
                    connector.onFileSelected()
                }
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    MainActivity.FILE_SELECT_CODE)
        } catch (ex: android.content.ActivityNotFoundException) {
            showToast(getString(R.string.install_file_manager))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            Utils.print("File Uri: ${uri?.toString()}")
            val path = uri?.path ?: ""
            Utils.print("File Path: $path")
            presenter.saveFilePath(path)
            connector.onFileSelected()
        } else {
            showToast("Something wrong :(")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showToast(s: String) {
        Toast.makeText(activity?.applicationContext, s, Toast.LENGTH_LONG).show()
    }
}