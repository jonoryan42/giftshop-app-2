package controllers

import models.Gift
import persistence.Serializer
import utils.Utilities.isValidListIndex

class GiftAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var gifts = ArrayList<Gift>()

    fun add(gift: Gift): Boolean {
        return gifts.add(gift)
    }

    fun deleteGift(indexToDelete: Int): Gift? {
        return if (isValidListIndex(indexToDelete, gifts)) {
            gifts.removeAt(indexToDelete)
        } else null
    }

    fun listAllGifts(): String =
    if(gifts.isEmpty())
        "No Gifts stored"
    else formatListString(gifts)

    fun findGift(index: Int): Gift? {
        return if (isValidListIndex(index, gifts)) {
            gifts[index]
        } else null
    }

    fun updateGift(indexToUpdate: Int, gift: Gift?): Boolean {
        val foundGift = findGift(indexToUpdate)
        if ((foundGift != null) && (gift != null)) {
            foundGift.title = gift.title
            foundGift.category = gift.category
            foundGift.price = gift.price
            foundGift.stock = gift.stock
            return true
        }
        return false
    }

    fun searchByTitle(searchString: String) =
        formatListString(
            gifts.filter { gift -> gift.title.contains(searchString, ignoreCase = true) })

    private fun formatListString(giftsToFormat : List<Gift>) : String =
        giftsToFormat
            .joinToString (separator = "\n") { gift ->
                gifts.indexOf(gift).toString() + ": " + gift.toString() }

    @Throws(Exception::class)
    fun load() {
        gifts = serializer.read() as ArrayList<Gift>
    }
    @Throws(Exception::class)
    fun store() {
        serializer.write(gifts)
        println("Saving Gifts..")
    }
}