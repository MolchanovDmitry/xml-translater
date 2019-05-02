package com.di.penopllast.xmltranslater.presentation.controller.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.presentation.ui.screen.s2_choose_file.view.ChooseFileFragmentImpl
import com.di.penopllast.xmltranslater.presentation.controller.model.FragmentName
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.impl.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.impl.ChooseDestinationLanguagesFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view.SaveApiKeyFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.view.TranslateFragmentImpl
import android.view.MotionEvent
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenterImpl
import com.di.penopllast.xmltranslater.presentation.ui.widget.HelpPanel
import android.os.Build
import android.os.StrictMode
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.presentation.controller.connector.*
import com.di.penopllast.xmltranslater.presentation.controller.lazy.bindView
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_0_API_KEY
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_1_FILE
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_2_FROM_LOCALE
import com.di.penopllast.xmltranslater.presentation.controller.model.RingType.Companion.RING_3_TO_LOCALE
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainView, HelpPanel.OnHelpViewClickListener,
        SaveApiKeyConnector,
        ChooseFileConnector,
        ChooseLanguageConnector,
        FinishChooseDestinationLanguagesConnector,
        TranslateConnector {

    companion object {
        private const val SWIPE_DISTANCE = 100
    }

    private val helpPanel: HelpPanel? by bindView(R.id.help_panel)
    private val titleText: TextView? by bindView(R.id.text_title)
    private val titleLayout: ViewGroup? by bindView(R.id.layout_title)
    private var shareImage: ImageView? = null

    private val presenter: MainPresenter = MainPresenterImpl(this)
    private val handler = Handler()
    private var currentStep = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCustomActionBar()
        helpPanel?.setClickListener(this)

        showSaveYandexApiKeyFragment()
        presenter.saveUserLocale(getLocale())
        presenter.isApiKeyExist()
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
            helpPanel?.hide()
        }, 1000)
    }

    override fun showSaveYandexApiKeyFragment() {
        currentStep = 1
        titleText?.text = getString(R.string.title_api_key)
        //addFragment(SaveApiKeyFragmentImpl())
        val fragment = SaveApiKeyFragmentImpl()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder_layout, fragment)
                .hide(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    override fun showChooseFileFragment() {
        currentStep = 2
        hideCurrentFragment()
        helpPanel?.colorRing(0, true)
        titleText?.text = getString(R.string.title_choose_file)
        addFragment(ChooseFileFragmentImpl())
    }

    private fun showChooseLanguageFragment() {
        currentStep = 3
        hideCurrentFragment()
        helpPanel?.colorRing(1, true)
        titleText?.text = getString(R.string.title_choose_file_locale)
        addFragment(ChooseLanguageFragmentImpl())
    }

    private fun showChooseTranslateLanguagesFragment() {
        currentStep = 4
        hideCurrentFragment()
        helpPanel?.colorRing(2, true)
        titleText?.text = getString(R.string.title_choose_translate_languages)
        addFragment(ChooseDestinationLanguagesFragmentImpl())
    }

    private fun showTranslateFragment() {
        currentStep = 5
        hideCurrentFragment()
        helpPanel?.colorRing(3, true)
        titleText?.text = getString(R.string.title_translate)
        addFragment(TranslateFragmentImpl())
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }

    private fun hideCurrentFragment() {
        val lastFragmentIndex = supportFragmentManager.fragments.size - 1
        if (lastFragmentIndex >= 0) {
            val currentFragment = supportFragmentManager.fragments[lastFragmentIndex]
            supportFragmentManager.beginTransaction().hide(currentFragment).commit()
        }
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
            showToast(getString(R.string.complete_previous_step))
        } else {
            showChooseFileFragment()
            onSecondRingSuccessClick()
        }
    }

    override fun onThirdStepClick() {
        if (currentStep < 3) {
            showToast(getString(R.string.complete_previous_step))
        } else {
            showChooseLanguageFragment()
            onThirdRingSuccessClick()
        }
    }

    override fun onFourthStepClick() {
        if (currentStep < 4) {
            showToast(getString(R.string.complete_previous_step))
        } else {
            showChooseTranslateLanguagesFragment()
            onFourthRingSuccessClick()
        }
    }

    private fun onFirstRingSuccessClick() {
        helpPanel?.colorRing(RING_0_API_KEY, false)
    }

    private fun onSecondRingSuccessClick() {
        helpPanel?.colorRing(RING_0_API_KEY, true)
        helpPanel?.colorRing(RING_1_FILE, false)
    }

    private fun onThirdRingSuccessClick() {
        helpPanel?.colorRing(RING_1_FILE, true)
        helpPanel?.colorRing(RING_2_FROM_LOCALE, false)
    }

    private fun onFourthRingSuccessClick() {
        helpPanel?.colorRing(RING_2_FROM_LOCALE, true)
        helpPanel?.colorRing(RING_3_TO_LOCALE, false)
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
                    helpPanel?.show()
                }
                deltaX = x1 - x2
                if (deltaX > SWIPE_DISTANCE) {
                    helpPanel?.hide()
                }
                helpPanel?.let {
                    if (!it.isHidden()) {
                        if ((event.x > it.width && event.y < it.height) ||
                                (event.x < it.width && event.y > it.height))
                            helpPanel?.hide()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onTranslateFinish(resultFilePathList: ArrayList<String>) {
        handler.post {
            shareImage = ImageView(this)
            shareImage?.setImageResource(R.drawable.ic_share)
            shareImage?.alpha = 0F
            val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            params.marginEnd = resources.getDimensionPixelSize(R.dimen.dp_10)

            titleLayout?.addView(shareImage, params)
            shareImage?.animate()?.alpha(1F)?.duration = 500
            shareImage?.setOnClickListener { onShareClick(resultFilePathList) }
        }
    }

    override fun onTranslateScreenCancel() {
        shareImage?.let {
            it.animate().alpha(0F).withEndAction { titleLayout?.removeView(it) }
            shareImage = null
        }
    }

    private fun onShareClick(resultFilePathList: ArrayList<String>) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.putExtra(Intent.EXTRA_SUBJECT, "Translated files")
        intent.type = "*/*"

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val files = ArrayList<Uri>()

        resultFilePathList.forEach { path ->
            val file = File(path)
            val uri = Uri.fromFile(file)
            files.add(uri)
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (helpPanel?.isHidden() == true) {
            super.onBackPressed()
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                showFirstStackFragment()
            }
        } else {
            helpPanel?.hide()
        }
    }

    private fun showFirstStackFragment() {
        val lastFragmentIndex = supportFragmentManager.fragments.size - 1
        val currentFragment = supportFragmentManager.fragments[lastFragmentIndex]
        supportFragmentManager.beginTransaction().show(currentFragment).commit()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
