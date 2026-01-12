package com.marcusrunge.stjohannisuelzen.ui.terms

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * [ViewModel] for the [TermsFragment].
 *
 * This ViewModel is responsible for providing the URL of the terms of use document
 * to be displayed. The URL is retrieved from the string resources.
 *
 * @param context The application context, injected by Hilt. It's used to access string resources.
 */
@HiltViewModel
class TermsViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    /**
     * The private [MutableLiveData] that holds the URL for the terms of use HTML file.
     * It is initialized with the URL from the string resources.
     */
    private val _endpointUrl = MutableLiveData(context.getString(R.string.html_terms))

    /**
     * The public [LiveData] that exposes the terms of use URL.
     * UI components can observe this LiveData to get the URL.
     * This is an immutable view of [_endpointUrl] to prevent modification from outside the ViewModel.
     */
    val endpointUrl: LiveData<String> = _endpointUrl
}
