package com.marcusrunge.stjohannisuelzen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.models.YoutubeItem

/**
 * A [RecyclerView.Adapter] for displaying a list of YouTube videos.
 *
 * This adapter is responsible for taking a list of [YoutubeItem] objects and binding them
 * to the `viewholder_youtube.xml` layout. It uses data binding to efficiently update the
 * views and handles item clicks by invoking a provided lambda.
 *
 * @property youtubeItems The list of [YoutubeItem]s to display.
 * @property onClicked A lambda function that is invoked when a video item is clicked.
 *                     It provides the `videoId` of the clicked item.
 */
class YoutubeRecyclerViewAdapter(
    private val youtubeItems: List<YoutubeItem>,
    private val onClicked: ((videoId: String?) -> Unit)?
) :
    RecyclerView.Adapter<YoutubeRecyclerViewAdapter.ViewHolder>() {

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
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

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * This method should update the contents of the [ViewHolder.itemView] to reflect the item at
     * the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *               item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val youtubeItem = youtubeItems[position]
        // Set a click listener on the item view to handle user interaction.
        holder.itemView.setOnClickListener {
            onClicked?.invoke(youtubeItem.videoId)
        }
        // Bind the YoutubeItem data to the ViewHolder.
        holder.bind(youtubeItem)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = youtubeItems.size

    /**
     * A [RecyclerView.ViewHolder] that describes an item view and metadata about its place
     * within the RecyclerView.
     *
     * This ViewHolder is designed to work with data binding. It holds a [ViewDataBinding]
     * instance, which it uses to bind data to the layout.
     *
     * @property viewDataBinding The data binding instance for the item layout.
     */
    class ViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        /**
         * Binds a [YoutubeItem] object to the layout.
         *
         * This method sets the `item` variable in the data binding layout and executes
         * any pending bindings to update the UI immediately.
         *
         * @param item The [YoutubeItem] to bind to the view.
         */
        fun bind(item: YoutubeItem) {
            viewDataBinding.setVariable(BR.item, item)
            viewDataBinding.executePendingBindings()
        }
    }
}