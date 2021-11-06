package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

interface DailyMotto {
    /**
     * Gets the inspiration instance
     * @see Inspiration
     */
    val inspiration: Inspiration

    /**
     * Gets the quote instance
     * @see Quote
     */
    val quote: Quote
}