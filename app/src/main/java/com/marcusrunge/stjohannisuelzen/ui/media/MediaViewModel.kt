package com.marcusrunge.stjohannisuelzen.ui.media

import android.content.Context
import android.os.Message
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marcusrunge.stjohannisuelzen.BuildConfig
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.adapter.YoutubeRecyclerViewAdapter
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchList
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.models.YoutubeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiConnect: ApiConnect
) : ViewModelBase(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private const val YOUTUBE_SEARCHLIST = 1
        private const val ERROR = 2
    }

    private val youtubeItems: MutableList<YoutubeItem> = mutableListOf()
    private var _isRefreshing: Boolean = true
    val liveVideoId = MutableLiveData<String>()

    @get:Bindable
    var youtubeRecyclerViewAdapter: YoutubeRecyclerViewAdapter? =
        YoutubeRecyclerViewAdapter(youtubeItems) {
            liveVideoId.value = it
        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.youtubeRecyclerViewAdapter)
        }

    @get:Bindable
    var isRefreshing: Boolean
        get() = _isRefreshing
        set(value) {
            _isRefreshing = value
            notifyPropertyChanged(BR.refreshing)
        }

    init {
        getYoutubeSearchList()
    }

    override fun updateView(inputMessage: Message) {
        when (inputMessage.arg1) {
            YOUTUBE_SEARCHLIST -> {
                (inputMessage.obj as YoutubeSearchList).items.forEach {
                    youtubeItems.add(
                        YoutubeItem(
                            it.snippet.title,
                            it.snippet.thumbnails.default.url,
                            it.id.videoId
                        )
                    )
                }
                if (youtubeItems.size > 0) liveVideoId.value = youtubeItems[0].videoId
                youtubeRecyclerViewAdapter?.notifyDataSetChanged()
                isRefreshing = false
            }
            ERROR -> {
                Toast.makeText(context, (inputMessage.obj as String?), Toast.LENGTH_LONG).show()
                isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        isRefreshing = true
        getYoutubeSearchList()
    }

    private fun getYoutubeSearchList() {
        viewModelScope.launch {
            apiConnect.youTube.getYoutubeSearchList(
                BuildConfig.YOUTUBE_DATA_API_KEY,
                context.getString(R.string.youtube_channel),
                {
                    val message = Message()
                    message.what = UPDATE_VIEW
                    message.arg1 = YOUTUBE_SEARCHLIST
                    message.obj = it
                    handler.sendMessage(message)
                },
                {
                    val message = Message()
                    message.what = UPDATE_VIEW
                    message.arg1 = ERROR
                    message.obj = it
                    handler.sendMessage(message)
                }
            )
        }
    }
}