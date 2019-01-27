package com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.presenter.connector.ChooseLanguagesConnector
import com.di.penopllast.xmltranslater.presentation.ui.chooselanguages.data.ExtendedLocaleMatch
import kotlinx.android.synthetic.main.item_layout_with_checkboxs.view.*

class MatchLanguagesAdapter(private val langMap: List<ExtendedLocaleMatch>,
                            private val connector: ChooseLanguagesConnector)
    : RecyclerView.Adapter<MatchLanguagesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ViewGroup = view.layout_root
        val codeFromText: TextView = view.text_code_from
        val codeToText: TextView = view.text_code_to
        val describeText: TextView = view.text_description
        val checkBox: CheckBox = view.checkbox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_with_checkboxs, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return langMap.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.codeFromText.text = langMap[position].from
        holder.codeToText.text = langMap[position].to
        holder.describeText.text = langMap[position].toDescription
        holder.root.setOnClickListener {
            if (holder.checkBox.isChecked)
                connector.onLanguageSelected(langMap[position].to)
            else
                connector.onUnLanguageSelected(langMap[position].to)
        }
    }
}