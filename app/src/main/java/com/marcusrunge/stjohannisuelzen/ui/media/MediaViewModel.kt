package com.marcusrunge.stjohannisuelzen.ui.media

import android.annotation.SuppressLint
import android.content.Context
import android.os.Message
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.adapter.YoutubeRecyclerViewAdapter
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchList
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.models.YoutubeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiConnect: ApiConnect,
    private val core: Core
) : ViewModelBase(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private const val YOUTUBE_SEARCHLIST = 1
        private const val ERROR = 2
    }

    private val _data = MutableLiveData(createHtmlData("M7lc1UVf-VE"))
    private val youtubeItems: MutableList<YoutubeItem> = mutableListOf()
    private var _isRefreshing: Boolean = true

    val data: LiveData<String> = _data

    @get:Bindable
    var youtubeRecyclerViewAdapter: YoutubeRecyclerViewAdapter? =
        YoutubeRecyclerViewAdapter(youtubeItems) {
            _data.value = it?.let { it1 -> createHtmlData(it1) }
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

    val origin: String = "https://app.stjohannisuelzen.marcusrunge.com"

    val mimeType: String = "text/html"

    val encoding: String = "UTF-8"

    init {
        getYoutubeSearchList()
    }

    @SuppressLint("NotifyDataSetChanged")
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
                if (youtubeItems.isNotEmpty()) _data.value = youtubeItems[0].videoId?.let {
                    createHtmlData(
                        it
                    )
                }
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
            val yt=core.apiKeys.youtube
            apiConnect.youTube.getYoutubeSearchList(
                yt,
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

    private fun createHtmlData(videoId: String): String {
        return """
        <html>
            <body style="margin:0">
                <iframe id="ytplayer"
                        type="text/html"
                        width="100%"
                        height="100%"
                        src="https://www.youtube.com/embed/$videoId?enablejsapi=1&origin=$origin"
                        frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen
                        referrerpolicy="strict-origin-when-cross-origin">
                </iframe>
                <script>
                    var tag = document.createElement('script');
                    tag.src = "https://www.youtube.com/iframe_api";
                    var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('ytplayer', {
                            events: {
                                'onReady': onPlayerReady,
                                'onStateChange': onPlayerStateChange
                            }
                        });
                    }
                    function onPlayerReady(event) {
                        event.target.playVideo();
                    }
                    var done = false;
                    function onPlayerStateChange(event) {
                        if (event.data == YT.PlayerState.PLAYING && !done) {
                            setTimeout(stopVideo, 6000);
                            done = true;
                        }
                    }
                    function stopVideo() {
                        player.stopVideo();
                    }
                    window.addEventListener("load", playerSizer);
                    window.addEventListener("resize", playerSizer);
                    function playerSizer() {
                        var player = document.getElementById("ytplayer");
                        var width = player.offsetWidth;
                        player.style.height = (width * 0.5625) + "px";
                    }
                </script>
            </body>
        </html>
    """
    }
}