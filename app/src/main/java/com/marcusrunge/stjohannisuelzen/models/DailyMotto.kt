package com.marcusrunge.stjohannisuelzen.models

/**
 * Represents the daily motto, which consists of a quote and an inspiration.
 *
 * This data class holds the content and verse for both the daily quote (Losung)
 * and the inspirational text (Lehrtext).
 *
 * @property quoteContent The text of the daily quote.
 * @property quoteVerse The Bible verse reference for the daily quote.
 * @property inspirationContent The text of the inspirational passage.
 * @property inspirationVerse The Bible verse reference for the inspirational passage.
 */
data class DailyMotto(
    val quoteContent: String?,
    val quoteVerse: String?,
    val inspirationContent: String?,
    val inspirationVerse: String?
)
