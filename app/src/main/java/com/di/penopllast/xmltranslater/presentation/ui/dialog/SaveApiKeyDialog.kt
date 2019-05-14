package com.di.penopllast.xmltranslater.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import com.di.penopllast.xmltranslater.R

class SaveApiKeyDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val nullParent: ViewGroup? = null
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.dialog_fragment_save_api_key, nullParent)

        val builder = AlertDialog.Builder(
                ContextThemeWrapper(activity, R.style.Theme_AppCompat))
                .setView(view)
        return builder.create()
    }

    override fun onDestroyView() {
        dialog?.setDismissMessage(null)
        super.onDestroyView()
    }
}