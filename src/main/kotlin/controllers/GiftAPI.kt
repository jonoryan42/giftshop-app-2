package controllers

import models.Gift
import persistence.Serializer

class GiftAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var gifts = ArrayList<Gift>()

    fun add(gift: Gift): Boolean {
        return gifts.add(gift)
    }

//    fun deleteGift(indexToDelete: Int): Gift? {
//        return if (isValidListIndex(indexToDelete, gifts)) {
//            gifts.removeAt(indexToDelete)
//        } else null
//    }

    fun listAllGifts(): String =
        if (gifts.isEmpty())
            "No Gifts stored"
        else formatListString(gifts)

//    fun findGift(index: Int): Gift? {
//        return if (isValidListIndex(index, gifts)) {
//            gifts[index]
//        } else null
//    }

//    fun updateGift(giftId: String, gift: Gift?): Boolean {
//        val foundGift = findGift(giftId)
//        if (foundGift != null) && (gift != null) {
//            foundGift.giftId = gift.giftId
//            foundGift.title = gift.title
//            foundGift.category = gift.category
//            foundGift.price = gift.price
//            foundGift.stock = gift.stock
//            return true
//        }
//        return false
//    }

    fun searchByTitle(searchString: String) =
        formatListString(
            gifts.filter { gift -> gift.title.contains(searchString, ignoreCase = true) })

    fun searchById(searchString: String) =
        formatListString(
            gifts.filter { gift -> gift.giftId.contains(searchString, ignoreCase = true) })

    fun findGift(giftId : String) =  gifts.find{ gift -> gift.giftId == giftId }

    @Throws(Exception::class)
    fun load() {
        gifts = serializer.read() as ArrayList<Gift>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(gifts)
        println("Saving Gifts..")
    }

    fun getPrice(gift: Gift?) = gift?.price

    private fun formatListString(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString( "\n") { gift -> "ID. ${gift.giftId}. " +
                    "${gift.title} / ${gift.price} / In Stock: ${gift.stock} / ${gift.category}"}

    }




