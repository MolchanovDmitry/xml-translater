package com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.model.ExtendedLocaleMatch
import com.di.penopllast.xmltranslater.presentation.ui.screen.s4_choose_languages.view.SelectLanguagesCallback
import kotlinx.android.synthetic.main.item_layout_with_checkboxs.view.*

class MatchLanguagesAdapter(private val langMap: List<ExtendedLocaleMatch>,
                            private val callback: SelectLanguagesCallback?)
    : RecyclerView.Adapter<MatchLanguagesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ViewGroup = view.layout_root
        val localeMapText: TextView = view.text_locales
        val describeText: TextView = view.text_description
        val checkBox: CheckBox = view.checkbox

        init {
            checkBox.isClickable = false//focus event only for root
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_with_checkboxs, parent, false))
    }

    override fun getItemCount(): Int {
        return langMap.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val localeMap = "${langMap[position].from}-${langMap[position].to}"
        holder.localeMapText.text = localeMap
        holder.describeText.text = langMap[position].toDescription
        holder.root.setOnClickListener {
            if (holder.checkBox.isChecked) {
                holder.checkBox.isChecked = false
                callback?.onUnLanguageSelected(langMap[position].to)
            } else {
                holder.checkBox.isChecked = true
                callback?.onLanguageSelected(langMap[position].to)
            }
        }
    }
}