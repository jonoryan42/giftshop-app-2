package controllers

import models.Gift
import persistence.Serializer

class GiftAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var gifts = ArrayList<Gift>()

    fun add(gift: Gift): Boolean {
        return gifts.add(gift)
    }

    fun delete(id: Int) = gifts.removeIf { gift -> gift.giftId == id}


    fun listAllGifts(): String =
        if (gifts.isEmpty())
            "No Gifts stored"
        else formatListString(gifts)

    fun listByCategory(category: String): String =
         if (gifts.isEmpty())
            "No Gifts stored"
        else {
             val listOfGifts = formatListString(gifts.filter { gift: Gift ->
                 gift.category == category
             })
             if (listOfGifts == "") "No Gifts of Category: $category"
                  else "\n${numberOfGiftsByCategory(category)} Gifts of Category: $category.\n$listOfGifts"
             }

    fun numberOfGifts(): Int = gifts.size

    fun numberOfGiftsByCategory(category: String): Int = gifts.count {gift: Gift ->
        gift.category == category}

    fun searchByTitle(searchString: String) =
        formatListString(
            gifts.filter { gift -> gift.title.contains(searchString, ignoreCase = true) })

    fun findGift(giftId : Int) =  gifts.find{ gift -> gift.giftId == giftId }

    @Throws(Exception::class)
    fun load() {
        gifts = serializer.read() as ArrayList<Gift>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(gifts)
    }

    private fun formatListString(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString( "\n") { gift -> "ID. ${gift.giftId}. " +
                    "${gift.title} / Price: ${gift.price} / In Stock: ${gift.stock} / ${gift.category}"}

    }




