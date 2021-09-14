package com.marcusrunge.stjohannisuelzen.ui.media

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcusrunge.stjohannisuelzen.BuildConfig
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val apiConnect: ApiConnect
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            var youtubeSearchList = apiConnect.youTube.getYoutubeSearchList(
                BuildConfig.YOUTUBE_DATA_API_KEY,
                context.getString(R.string.youtube_channel)
            )
        }
    }
}