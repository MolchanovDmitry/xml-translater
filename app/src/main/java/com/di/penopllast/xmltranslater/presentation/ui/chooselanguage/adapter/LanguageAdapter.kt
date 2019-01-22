package com.di.penopllast.xmltranslater.presentation.ui.chooselanguage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.acitvity.connector.ChooseLanguageConnector
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.item_lang.view.*

class LanguageAdapter(
        val langs: LinkedTreeMap<String, String>,
        val connector: ChooseLanguageConnector
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.root_layout
        val codeText = view.code_text
        val describeText = view.describe_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lang, parent, false))
    }

    override fun getItemCount(): Int {
        return langs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locale = langs.keys.elementAt(position)
        holder.codeText.text = locale
        holder.describeText.text = langs.values.elementAt(position)
        holder.root.setOnClickListener { connector.onLanguageSelected(locale) }
    }
}