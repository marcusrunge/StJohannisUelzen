package com.marcusrunge.stjohannisuelzen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.models.LinkButton

class LinkButtonsRecyclerViewAdapter(
    private val linkButtons: Array<LinkButton>,
    private val onClicked: ((url: String) -> Unit)?
) : RecyclerView.Adapter<LinkButtonsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: MaterialButton = view.findViewById(R.id.link_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_linkbutton, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val linkButton = linkButtons[position]
        holder.button.text = linkButton.text
        holder.itemView.setOnClickListener {
            onClicked?.invoke(linkButton.url)
        }
    }

    override fun getItemCount(): Int {
        return linkButtons.size
    }
}