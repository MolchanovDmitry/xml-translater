package com.di.penopllast.xmltranslater.presentation.presenter

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.application.XmlTranslaterApp
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import com.di.penopllast.xmltranslater.presentation.presenter.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.presenter.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.presenter.connector.FinishChooseDestinationLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.ui.choosefile.ChooseFileFragment
import com.di.penopllast.xmltranslater.presentation.ui.Fragment
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.view.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.view.ChooseDestinationLanguagesFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.saveapikey.SaveApiKeyFragment
import com.di.penopllast.xmltranslater.presentation.ui.translate.view.TranslateFragmentImpl
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView,
        ChooseFileConnector,
        ChooseLanguageConnector,
        FinishChooseDestinationLanguagesConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
    }

    lateinit var repositoryPreference: RepositoryPreference @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        XmlTranslaterApp.app.componentsHolder.appComponent.inject(this)

        if (savedInstanceState == null) {
            showSaveYandexApiKeyFragment()
            //showChooseFileFragment()
            /*repositoryPreference.setFilePath("/sdcard/strings.xml")
            showChooseLanguageFragment()*/
            //showTranslageFragment()
        }
    }

    private fun showSaveYandexApiKeyFragment() {
        title = "Save translate api key"
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder_layout, SaveApiKeyFragment(),
                        Fragment.SAVE_API_KEY)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseFileFragment() {
        title = "Choose file"
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
                .replace(R.id.fragment_placeholder_layout, ChooseLanguageFragmentImpl(),
                        Fragment.CHOOSE_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    private fun showChooseTranslateLanguagesFragment() {
        title = "Choose Translate Languages"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout,
                        ChooseDestinationLanguagesFragmentImpl(),
                        Fragment.CHOOSE_TRANSLATION_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showTranslageFragment() {
        title = "Translate"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout,
                        TranslateFragmentImpl(),
                        Fragment.TRANSLATE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()

    }

    override fun onLanguageSelected(locale: String) {
        showChooseTranslateLanguagesFragment()
    }

    override fun onFinishChooseDestinationLanguages() {
        showTranslageFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            Utils.print("File Uri: ${uri?.toString()}")
            val path = uri?.path ?: ""
            Utils.print("File Path: $path")
            repositoryPreference.setFilePath(path)
            showChooseLanguageFragment()
        } else {
            Toast.makeText(this, "Something wrong :(", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
