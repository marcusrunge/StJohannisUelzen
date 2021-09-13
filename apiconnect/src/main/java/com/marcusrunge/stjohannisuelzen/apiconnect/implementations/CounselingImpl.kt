package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.Counseling

internal class CounselingImpl : Counseling {
    companion object {
        private var counseling: Counseling? = null
        fun create(): Counseling = when {
            counseling != null -> counseling!!
            else -> {
                counseling = CounselingImpl()
                counseling!!
            }
        }
    }
}