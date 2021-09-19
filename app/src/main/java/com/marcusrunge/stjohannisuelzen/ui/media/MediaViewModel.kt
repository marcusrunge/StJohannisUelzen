package com.marcusrunge.stjohannisuelzen.ui.media

import android.content.Context
import android.os.Message
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import com.marcusrunge.stjohannisuelzen.BuildConfig
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.adapter.YoutubeRecyclerViewAdapter
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.models.Items
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val apiConnect: ApiConnect
) : ViewModelBase() {
    companion object {
        private const val YOUTUBE_SEARCHLIST = 1
        private const val ERROR = 2
    }

    private val youtubeItems: MutableList<Items> = mutableListOf()

    @get:Bindable
    var youtubeRecyclerViewAdapter: YoutubeRecyclerViewAdapter? =
        YoutubeRecyclerViewAdapter(youtubeItems) {
            //TODO: Load video on click
        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.youtubeRecyclerViewAdapter)
        }

    init {
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

    override fun updateView(inputMessage: Message) {
        when (inputMessage.arg1) {
            YOUTUBE_SEARCHLIST -> {
                (inputMessage.obj as List<Items>).forEach {
                    youtubeItems.add(it)
                }
            }
            ERROR -> {
                //TODO: Show error
            }
        }
    }
}