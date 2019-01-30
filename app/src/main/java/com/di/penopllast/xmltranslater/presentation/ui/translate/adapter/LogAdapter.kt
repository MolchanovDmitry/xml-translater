package com.di.penopllast.xmltranslater.presentation.ui.translate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import com.di.penopllast.xmltranslater.presentation.ui.translate.model.LogMap
import kotlinx.android.synthetic.main.item_log.view.*

class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private var logMapList = ArrayList<LogMap>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logText: TextView = view.log_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_log, parent, false))
    }

    override fun getItemCount(): Int {
        return logMapList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.logText.text = logMapList[position].str
        holder.logText.setBackgroundColor(logMapList[position].color)
    }

    fun addItem(s: String, color: Int) {
        logMapList.add(LogMap(s, color))
        notifyDataSetChanged()
    }

}