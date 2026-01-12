package com.marcusrunge.stjohannisuelzen.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Represents a YouTube video item, designed for use with data binding.
 *
 * This `data class` holds information about a YouTube video, including its title,
 * thumbnail URL, and video ID. It extends [BaseObservable] and uses `@Bindable`
 * properties to allow for automatic UI updates when the data changes.
 *
 * The primary constructor holds the internal backing fields, while the public properties
 * provide bindable getters and setters.
 *
 * @property title The title of the YouTube video.
 * @property thumbnailUrl The URL for the video's thumbnail image.
 * @property videoId The unique ID of the YouTube video.
 */
data class YoutubeItem(
    private var _title: String?,
    private var _thumbnailUrl: String?,
    private var _videoId: String?
) : BaseObservable() {

    @get:Bindable
    var title: String?
        get() = _title
        set(value) {
            if (_title != value) {
                _title = value
                notifyPropertyChanged(BR.title)
            }
        }

    @get:Bindable
    var thumbnailUrl: String?
        get() = _thumbnailUrl
        set(value) {
            if (_thumbnailUrl != value) {
                _thumbnailUrl = value
                notifyPropertyChanged(BR.thumbnailUrl)
            }
        }

    @get:Bindable
    var videoId: String?
        get() = _videoId
        set(value) {
            if (_videoId != value) {
                _videoId = value
                notifyPropertyChanged(BR.videoId)
            }
        }
}
