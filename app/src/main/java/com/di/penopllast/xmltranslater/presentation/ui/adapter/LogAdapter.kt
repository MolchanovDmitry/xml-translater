package com.di.penopllast.xmltranslater.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.di.penopllast.xmltranslater.R
import kotlinx.android.synthetic.main.item_log.view.*

class LogAdapter() : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private var stringList = ArrayList<String>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logText = view.log_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_log, parent, false))
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.logText.text = stringList[position]
    }

    public fun addItem(s: String) {
        stringList.add(s)
        notifyDataSetChanged()
    }

}