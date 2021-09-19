package com.marcusrunge.stjohannisuelzen.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class YoutubeItem(private var _title: String?, private var _thumbnailUrl: String?, private var _videoId: String?): BaseObservable(){
    @get:Bindable
    var title: String?
        get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var thumbnailUrl: String?
        get() = _thumbnailUrl
        set(value) {
            _thumbnailUrl = value
            notifyPropertyChanged(BR.thumbnailUrl)
        }

    @get:Bindable
    var videoId: String?
        get() = _videoId
        set(value) {
            _videoId = value
            notifyPropertyChanged(BR.videoId)
        }
}
