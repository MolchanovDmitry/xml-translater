package com.di.penopllast.xmltranslater.presentation.ui.main

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.util.ArrayMap
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.ui.main.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.ui.main.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.ui.ChooseFileFragment
import com.di.penopllast.xmltranslater.presentation.ui.Fragment
import com.di.penopllast.xmltranslater.presentation.ui.translate.view.TranslateFragment
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view.ChooseDestinationLanguagesFragmentImpl
import com.google.gson.internal.LinkedTreeMap


class MainActivity : AppCompatActivity(), MainView,
        ChooseFileConnector,
        ChooseLanguageConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showChooseLanguageFragment()
        }
    }

    private fun showChooseFileFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder_layout, ChooseFileFragment(),
                        Fragment.CHOOSE_FILE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseLanguageFragment() {
        title = "Choose file language"
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder_layout, ChooseLanguageFragmentImpl(),
                        Fragment.CHOOSE_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseTranslateLanguagesFragment(locale: String) {
        title = "Choose Translate Languages"
        val fragment = ChooseDestinationLanguagesFragmentImpl()
        val bundle = Bundle()
        bundle.putString("locale", locale)
        fragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, fragment,
                        Fragment.CHOOSE_TRANSLATION_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    fun onLanguageListFetched(langs: LinkedTreeMap<String, String>) {
        val langFragment = supportFragmentManager.findFragmentByTag(Fragment.CHOOSE_TRANSLATION_LANGUAGE)
        langFragment?.let {
            if (it is ChooseLanguageFragmentImpl && it.isVisible) {
                //it.fillRecycler(langs)
            }
        }
    }

    override fun onLanguageSelected(locale: String) {
        showChooseTranslateLanguagesFragment(locale)
    }

    override fun updateTranslateStatus(propMap: ArrayMap<String, Any>) {
        val langFragment = supportFragmentManager.findFragmentByTag(Fragment.TRANSLATE)
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
