package com.di.penopllast.xmltranslater.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguagesConnector
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.item_layout_with_checkboxs.view.*
import java.util.zip.Inflater

class LanguagesAdapter(val langs: LinkedTreeMap<String, String>,
                       val connector: ChooseLanguagesConnector)
    : RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.root_layout
        val codeText = view.code_text
        val describeText = view.describe_text
        val checkBox = view.checkbox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_with_checkboxs, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return langs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locale = langs.keys.elementAt(position)
        holder.codeText.text = locale
        holder.describeText.text = langs.values.elementAt(position)
        holder.root.setOnClickListener {
            if (holder.checkBox.isChecked) {
                connector.onLanguageSelected(locale)
            } else {
                connector.onUnLanguageSelected(locale)
            }
        }
    }
}