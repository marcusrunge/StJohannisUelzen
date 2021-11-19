package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Push {
    /**
     * Shows a notification message
     * @param textTitle The notification title
     * @param textContent The notification content
     * @param clazz The activity class to be shown on click
     */
    fun showSmall(textTitle: String?, textContent: String?, clazz: Class<*>?)

    /**
     * Shows a notification message
     * @param textTitle The notification title
     * @param textLine1 The first content text line
     * @param textLine2 The second content text line
     * @param clazz The activity class to be shown on click
     */
    fun showLarge(textTitle: String?, textLine1: String?, textLine2: String?, clazz: Class<*>?)
}