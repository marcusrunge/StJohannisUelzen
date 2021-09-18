package com.marcusrunge.stjohannisuelzen.ui.media

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcusrunge.stjohannisuelzen.BuildConfig
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val apiConnect: ApiConnect
) : ViewModel() {
    companion object {
        private const val UPDATE_VIEW = 1
        private const val YOUTUBE_SEARCHLIST = 1
        private const val ERROR = 2
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            when (inputMessage.what) {
                UPDATE_VIEW -> updateView(inputMessage)
            }
        }
    }

    init {
        viewModelScope.launch {
            async {
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

    private fun updateView(inputMessage: Message) {
        when (inputMessage.arg1) {
            YOUTUBE_SEARCHLIST -> {
                //TODO: Show list
            }
            ERROR -> {
                //TODO: Show error
            }
        }
    }
}