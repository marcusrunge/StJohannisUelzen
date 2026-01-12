package com.marcusrunge.stjohannisuelzen.models

/**
 * Represents a button that links to a URL.
 *
 * This data class is used to model the buttons displayed in the custom action bar,
 * where each button has a visible text and an associated URL to navigate to.
 *
 * @property text The text to be displayed on the button.
 * @property url The URL that the button should link to.
 */
data class LinkButton(val text: String, val url: String)
