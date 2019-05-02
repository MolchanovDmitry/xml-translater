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
import com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.view.impl.ChooseLanguageFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.impl.ChooseDestinationLanguagesFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s1_save_api_key.view.SaveApiKeyFragmentImpl
import com.di.penopllast.xmltranslater.presentation.ui.screen.s5_translate.view.TranslateFragmentImpl
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.controller.presenter.MainPresenterImpl
import android.os.Build
import android.os.StrictMode
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.di.penopllast.xmltranslater.presentation.controller.connector.*
import com.di.penopllast.xmltranslater.presentation.controller.lazy.bindView
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainView,
        SaveApiKeyConnector,
        ChooseFileConnector,
        ChooseLanguageConnector,
        FinishChooseDestinationLanguagesConnector,
        TranslateConnector {

    private val titleText: TextView? by bindView(R.id.text_title)
    private val titleLayout: ViewGroup? by bindView(R.id.layout_title)
    private var shareImage: ImageView? = null

    private val presenter: MainPresenter = MainPresenterImpl(this)
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCustomActionBar()

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

    override fun showSaveYandexApiKeyFragment() {
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
        hideCurrentFragment()
        titleText?.text = getString(R.string.title_choose_file)
        addFragment(ChooseFileFragmentImpl())
    }

    private fun showChooseLanguageFragment() {
        hideCurrentFragment()
        titleText?.text = getString(R.string.title_choose_file_locale)
        addFragment(ChooseLanguageFragmentImpl())
    }

    private fun showChooseTranslateLanguagesFragment() {
        hideCurrentFragment()
        titleText?.text = getString(R.string.title_choose_translate_languages)
        addFragment(ChooseDestinationLanguagesFragmentImpl())
    }

    private fun showTranslateFragment() {
        hideCurrentFragment()
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
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            showFirstStackFragment()
        }
    }

    private fun showFirstStackFragment() {
        val lastFragmentIndex = supportFragmentManager.fragments.size - 1
        val currentFragment = supportFragmentManager.fragments[lastFragmentIndex]
        supportFragmentManager.beginTransaction().show(currentFragment).commit()
    }
}
