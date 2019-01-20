package com.di.penopllast.xmltranslater.presentation.ui.acitvity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.util.ArrayMap
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.ui.fragment.ChooseFileFragment
import com.di.penopllast.xmltranslater.presentation.ui.fragment.TranslateFragment
import com.di.penopllast.xmltranslater.presentation.ui.fragment.impl.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.fragment.impl.TranslateFragmentImpl
import com.google.gson.internal.LinkedTreeMap


class MainActivity : AppCompatActivity(), MainView,
        ChooseFileConnector,
        ChooseLanguageConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
    }

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenterImpl(this)
        //presenter.getLangs()
        presenter.translate()

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder_layout, TranslateFragmentImpl(),
                            "TranslateFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }

    private fun showChooseFileFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseFileFragment(),
                        "ChooseFileFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    private fun showChooseLanguageFragment(){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseLanguageFragmentImpl(),
                        "ChooseLanguageFragmentImpl")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    private fun showTranslateFragment(){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, TranslateFragmentImpl(),
                        "TranslateFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    override fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        val langFragment = supportFragmentManager.findFragmentByTag("ChooseLanguageFragmentImpl")
        langFragment?.let {
            if (it is ChooseLanguageFragmentImpl && it.isVisible) {
                it.fillRecycler(langs)
            }
        }
    }

    override fun onLanguageSelected(locale: String) {
        showToast(locale)
    }

    override fun updateTranslateStatus(propMap: ArrayMap<String, Any>) {
        val langFragment = supportFragmentManager.findFragmentByTag("TranslateFragment")
        langFragment?.let {
            if (it is TranslateFragment && it.isVisible) {
                it.updateFragmentTranslateStatus(propMap)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> if (resultCode == Activity.RESULT_OK) {
                val uri = data?.data
                Utils.print("File Uri: ${uri?.toString()}")
                val path = uri?.path
                Utils.print("File Path: $path")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
