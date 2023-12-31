package utils

import models.Gift
import java.util.*
import kotlin.math.round

object Utilities {

    fun formatListString(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString(separator = "\n") { gift -> "$gift"}
    //for testing

    /**
     * Rounds double down to two decimal places.
     */
    fun roundTwoDecimals(number: Double) = round(number * 100) / 100

    }