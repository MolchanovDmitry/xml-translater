package com.di.penopllast.xmltranslater.presentation.controller.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.os.Handler
import android.view.LayoutInflater
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.FinishChooseDestinationLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.SaveApiKeyConnector
import com.di.penopllast.xmltranslater.presentation.ui.s2_choose_file.view.ChooseFileFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.Fragment
import com.di.penopllast.xmltranslater.presentation.ui.s3_choose_language.view.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.s4_choose_languages.view.ChooseDestinationLanguagesFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.s1_save_api_key.view.SaveApiKeyFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.s5_translate.view.TranslateFragmentImpl
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.widget.FrameLayout
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.widget.HelpPanel
import kotlinx.android.synthetic.main.custom_title.*


class MainActivity : AppCompatActivity(), MainView, HelpPanel.OnHelpViewClickListener,
        SaveApiKeyConnector,
        ChooseFileConnector,
        ChooseLanguageConnector,
        FinishChooseDestinationLanguagesConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
        private const val SWIPE_DISTANCE = 150
    }

    private lateinit var presenter: MainPresenter
    private val handler = Handler()
    private var currentStep = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCustomActionBar()
        help_panel.setClickListener(this)

        presenter = MainPresenterImpl(this)
        if (presenter.isApiKeyExist()) {
            showChooseFileFragment()
        } else {
            showSaveYandexApiKeyFragment()
        }

        /*if (savedInstanceState == null) {
            showSaveYandexApiKeyFragment()
            //showChooseFileFragment()
            *//*repositoryPreference.setFilePath("/sdcard/strings.xml")
            showChooseLanguageFragment()*//*
            //showTranslageFragment()
        }*/
    }

    private fun initCustomActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_title, null)
        v.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        actionBar?.title = ""
        actionBar?.customView = v
    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed({
            help_panel.hide()
        }, 1000)
    }

    private fun showSaveYandexApiKeyFragment() {
        currentStep = 1
        text_title.text = "Save translate api key"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, SaveApiKeyFragmentImpl(),
                        Fragment.SAVE_API_KEY)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseFileFragment() {
        currentStep = 2
        help_panel.colorRing(0, true)
        text_title.text = "Choose file"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseFileFragmentImpl(),
                        Fragment.CHOOSE_FILE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseLanguageFragment() {
        currentStep = 3
        help_panel.colorRing(1, true)
        text_title.text = "Choose file language"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseLanguageFragmentImpl(),
                        Fragment.CHOOSE_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    private fun showChooseTranslateLanguagesFragment() {
        currentStep = 4
        help_panel.colorRing(2, true)
        text_title.text = "Choose Translate Languages"
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
        currentStep = 5
        help_panel.colorRing(3, true)
        text_title.text = "Translate"
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout,
                        TranslateFragmentImpl(),
                        Fragment.TRANSLATE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()

    }

    override fun onSaveApiKey() {
        showChooseFileFragment()
    }

    override fun onFileSelected() {
        showChooseLanguageFragment()
    }

    override fun onLanguageSelected(locale: String) {
        showChooseTranslateLanguagesFragment()
    }

    override fun onFinishChooseDestinationLanguages() {
        showTranslageFragment()
    }

    override fun onFirstStepClick() {
        showSaveYandexApiKeyFragment()
        help_panel.colorRing(0, false)
    }

    override fun onSecondStepClick() {
        if (currentStep < 2) {
            showToast("Complete previous step")
        } else {
            showChooseFileFragment()
            help_panel.colorRing(0, true)
            help_panel.colorRing(1, false)
        }
    }

    override fun onThirdStepClick() {
        if (currentStep < 3) {
            showToast("Complete previous steps")
        } else {
            showChooseLanguageFragment()
            help_panel.colorRing(1, true)
            help_panel.colorRing(2, false)
        }
    }

    override fun onFourthStepClick() {
        if (currentStep < 4) {
            showToast("Complete previous steps")
        } else {
            showChooseTranslateLanguagesFragment()
            help_panel.colorRing(2, true)
            help_panel.colorRing(3, false)
        }
    }

    private var x1 = 0F
    private var x2 = 0F
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> x1 = event.x
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                var deltaX = x2 - x1
                Utils.print("1488 1 $deltaX")
                if (deltaX > SWIPE_DISTANCE) {
                    help_panel.show()
                }
                deltaX = x1 - x2
                if (deltaX > SWIPE_DISTANCE) {
                    help_panel.hide()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        if (help_panel.isHidden()) {
            super.onBackPressed()
        } else {
            help_panel.hide()
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
