package com.marcusrunge.stjohannisuelzen.dailymotto.utils

internal class StringUtil {
    companion object {
        fun String.normalize():String{
            val stringWithoutLineBreaks = this.replace("\n", "")
            var result=String()
            for (indice in stringWithoutLineBreaks.indices) {
               if(stringWithoutLineBreaks[indice] == ' ' && indice-1>-1&& stringWithoutLineBreaks[indice-1]== ' ') continue
                else result=result.plus(stringWithoutLineBreaks[indice])
            }
            return result
        }
    }
}