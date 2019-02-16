package com.di.penopllast.xmltranslater.presentation.controller.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.os.Handler
import android.view.LayoutInflater
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseLanguageConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.FinishChooseDestinationLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.controller.connector.SaveApiKeyConnector
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view.ChooseFileFragmentImpl
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.impl.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.impl.ChooseDestinationLanguagesFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view.SaveApiKeyFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.view.TranslateFragmentImpl
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.widget.FrameLayout
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.widget.HelpPanel
import kotlinx.android.synthetic.main.custom_title.*
import android.os.Build
import android.view.ViewGroup
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_0_API_KEY
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_1_FILE
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_2_FROM_LOCALE
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_3_TO_LOCALE
import java.util.*


class MainActivity : AppCompatActivity(), MainView, HelpPanel.OnHelpViewClickListener,
        SaveApiKeyConnector,
        ChooseFileConnector,
        ChooseLanguageConnector,
        FinishChooseDestinationLanguagesConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
        private const val SWIPE_DISTANCE = 150
    }

    private val presenter: MainPresenter = MainPresenterImpl(this)
    private val handler = Handler()
    private var currentStep = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCustomActionBar()
        help_panel.setClickListener(this)
        showSaveYandexApiKeyFragment()

        presenter.saveUserLocale(getLocale())
        if (presenter.isApiKeyExist()) {
            showChooseFileFragment()
        }
    }

    private fun getLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            resources.configuration.locale
        }
    }

    private fun initCustomActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup: ViewGroup? = null
        val v = inflater.inflate(R.layout.custom_title, viewGroup)
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
        text_title.text = getString(R.string.title_api_key)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, SaveApiKeyFragmentImpl(),
                        FragmentName.SAVE_API_KEY)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseFileFragment() {
        currentStep = 2
        help_panel.colorRing(0, true)
        text_title.text = getString(R.string.title_choose_file)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseFileFragmentImpl(),
                        FragmentName.CHOOSE_FILE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseLanguageFragment() {
        currentStep = 3
        help_panel.colorRing(1, true)
        text_title.text = getString(R.string.title_choose_file_locale)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout, ChooseLanguageFragmentImpl(),
                        FragmentName.CHOOSE_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseTranslateLanguagesFragment() {
        currentStep = 4
        help_panel.colorRing(2, true)
        text_title.text = getString(R.string.title_choose_translate_languages)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout,
                        ChooseDestinationLanguagesFragmentImpl(),
                        FragmentName.CHOOSE_TRANSLATION_LANGUAGE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun showTranslateFragment() {
        currentStep = 5
        help_panel.colorRing(3, true)
        text_title.text = getString(R.string.title_translate)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder_layout,
                        TranslateFragmentImpl(),
                        FragmentName.TRANSLATE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()

    }

    override fun onResumeFragment(@FragmentName fragmentName: String) {
        when (fragmentName) {
            FragmentName.SAVE_API_KEY -> onFirstRingSuccessClick()
            FragmentName.CHOOSE_FILE -> onSecondRingSuccessClick()
            FragmentName.CHOOSE_LANGUAGE -> onThirdRingSuccessClick()
            FragmentName.CHOOSE_TRANSLATION_LANGUAGE -> onFourthRingSuccessClick()
        }
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
        showTranslateFragment()
    }

    override fun onFirstStepClick() {
        showSaveYandexApiKeyFragment()
        onFirstRingSuccessClick()
    }

    override fun onSecondStepClick() {
        if (currentStep < 2) {
            showToast("Complete previous step")
        } else {
            showChooseFileFragment()
            onSecondRingSuccessClick()
        }
    }

    override fun onThirdStepClick() {
        if (currentStep < 3) {
            showToast("Complete previous steps")
        } else {
            showChooseLanguageFragment()
            onThirdRingSuccessClick()
        }
    }

    override fun onFourthStepClick() {
        if (currentStep < 4) {
            showToast("Complete previous steps")
        } else {
            showChooseTranslateLanguagesFragment()
            onFourthRingSuccessClick()
        }
    }

    private fun onFirstRingSuccessClick() {
        help_panel.colorRing(RING_0_API_KEY, false)
    }

    private fun onSecondRingSuccessClick() {
        help_panel.colorRing(RING_0_API_KEY, true)
        help_panel.colorRing(RING_1_FILE, false)
    }

    private fun onThirdRingSuccessClick() {
        help_panel.colorRing(RING_1_FILE, true)
        help_panel.colorRing(RING_2_FROM_LOCALE, false)
    }

    private fun onFourthRingSuccessClick() {
        help_panel.colorRing(RING_2_FROM_LOCALE, true)
        help_panel.colorRing(RING_3_TO_LOCALE, false)
    }

    private var x1 = 0F
    private var x2 = 0F
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> x1 = event.x
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                var deltaX = x2 - x1
                if (deltaX > SWIPE_DISTANCE) {
                    help_panel.show()
                }
                deltaX = x1 - x2
                if (deltaX > SWIPE_DISTANCE) {
                    help_panel.hide()
                }
                if (!help_panel.isHidden()) {
                    if ((event.x > help_panel.width && event.y < help_panel.height) ||
                            (event.x < help_panel.width && event.y > help_panel.height))
                        help_panel.hide()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        if (help_panel.isHidden()) {
            super.onBackPressed()
            if (supportFragmentManager.backStackEntryCount == 0)
                finish()
        } else {
            help_panel.hide()
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
