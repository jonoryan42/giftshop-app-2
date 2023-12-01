package utils

import models.Gift

object Utilities {

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun formatListString(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString(separator = "\n") { gift -> "$gift"}

//    fun formatListYes(giftsToFormat:  List<Gift>): String =
//        giftsToFormat
//            .joinToString(separator = "\n")
}