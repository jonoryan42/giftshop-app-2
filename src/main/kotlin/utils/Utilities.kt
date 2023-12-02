package utils

import models.Gift
import java.util.*
import kotlin.math.round

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
    //for testing

    fun roundTwoDecimals(number: Double) = round(number * 100) / 100

//    fun capital(): String = toString().replaceFirstChar { if (it.isLowerCase())
//        it.titlecase(Locale.getDefault())
//    else it.toString()

    }