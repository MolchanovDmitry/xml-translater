package com.di.penopllast.xmltranslater.presentation.ui.acitvity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.application.utils.Utils
import com.di.penopllast.xmltranslater.presentation.presenter.MainPresenter
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseFileConnector
import com.di.penopllast.xmltranslater.presentation.ui.fragment.ChooseFileFragment


class MainActivity : AppCompatActivity(), MainView,
        ChooseFileConnector {

    companion object {
        internal const val FILE_SELECT_CODE = 0
    }

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //presenter = MainPresenterImpl()
        //presenter.getLangs()

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder_layout, ChooseFileFragment(),
                            "ChooseFileFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
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
