package com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter.ChooseFilePresenter
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.presenter.ChooseFilePresenterImpl
import kotlinx.android.synthetic.main.fragment_choose_file.*
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.domain.model.FileType
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName


class ChooseFileFragmentImpl : Fragment(), ChooseFileFragment {

    private val presenter: ChooseFilePresenter = ChooseFilePresenterImpl(this)
    private val connector: ChooseFileConnector? by lazy { context as ChooseFileConnector }
    private val activity: Activity? by lazy { context as Activity }
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connector?.onResumeFragment(FragmentName.CHOOSE_FILE)
        context?.let { context ->
            ArrayAdapter.createFromResource(
                    context,
                    R.array.file_type,
                    android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner_file_type.adapter = adapter
            }
            choose_file_button.setOnClickListener {
                if (spinner_file_type.selectedItemPosition == 0) {
                    spinner_file_type.performClick()
                    showToast("Choose file type")
                } else {
                    showFileChooser()
                }
            }
        }
        checkPermission()
    }

    private fun checkPermission() {
        activity?.let { activity ->
            val permission = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        RECORD_REQUEST_CODE)
            }
        }
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
                    connector?.onFileSelected()
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
                    FILE_REQUEST_CODE)
        } catch (ex: ActivityNotFoundException) {
            showToast(getString(R.string.install_file_manager))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data
                    Utils.print("File Uri: ${uri?.toString()}")
                    val path = uri?.path ?: ""
                    presenter.saveFilePath(path)
                    presenter.saveFileType(getFileType(spinner_file_type.selectedItemPosition))
                    connector?.onFileSelected()
                } else {
                    showToast("Something wrong :(")
                }
            }
            RECORD_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_CANCELED) {
                    showToast("You must grant write permission")
                    checkPermission()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showToast(s: String) {
        Toast.makeText(activity?.applicationContext, s, Toast.LENGTH_LONG).show()
    }

    private fun getFileType(spinnerItemPosition: Int) = when (spinnerItemPosition) {
        1 -> FileType.XML
        2 -> FileType.PHP
        3 -> FileType.STRINGS
        else -> -1
    }

    companion object {
        const val FILE_REQUEST_CODE = 0
        const val RECORD_REQUEST_CODE = 1
    }
}