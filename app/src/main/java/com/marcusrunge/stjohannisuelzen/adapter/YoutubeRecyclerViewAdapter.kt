package com.marcusrunge.stjohannisuelzen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.models.YoutubeItem

class YoutubeRecyclerViewAdapter(
    private val youtubeItems: List<YoutubeItem>,
    private val onClicked: ((videoId: String?) -> Unit)?
) :
    RecyclerView.Adapter<YoutubeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.viewholder_youtube,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val youtubeItem = youtubeItems[position]
        holder.itemView.setOnClickListener {
            onClicked?.invoke(youtubeItem.videoId)
        }
        holder.bind(youtubeItem)
    }

    override fun getItemCount(): Int = youtubeItems.size

    class ViewHolder internal constructor(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(`object`: Any?) {
            viewDataBinding.setVariable(BR.item, `object`)
            viewDataBinding.executePendingBindings()
        }
    }
}