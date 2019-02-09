package com.di.penopllast.xmltranslater.presentation.ui.screen.s3_choose_language.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.controller.connector.ChooseLanguageConnector
import kotlinx.android.synthetic.main.item_lang.view.*

class LanguageAdapter(
        private val langs: ArrayMap<String, String>,
        private val connector: ChooseLanguageConnector
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ViewGroup = view.root_layout
        val codeText: TextView = view.code_text
        val describeText: TextView = view.describe_text
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