package controllers

import models.Gift
import utils.Utilities.isValidListIndex

//import persistence.Serializer

//import kotlin.math.round

class BagAPI(/*serializerType: Serializer*/) {
//    private var serializer: Serializer = serializerType

    private var bag = ArrayList<Gift>()

    fun add(gift: Gift): Boolean {
        return bag.add(gift)
    }

//    fun addByIndex(indexToAdd: Int) :

//    fun addToBag(indexToAdd: Int): Gift? {
//        return if (isValidListIndex(indexToAdd, bag)) {
//            bag.add(indexToAdd)
//        } else null
//    }

    fun deleteGift(indexToDelete: Int): Gift? {
        return if (isValidListIndex(indexToDelete, bag)) {
            bag.removeAt(indexToDelete)
        } else null
    }

    fun listAllGifts(): String =
        if (bag.isEmpty())
            "No Gifts stored"
        else formatListBag(bag)

    fun listShopping(): String = """
           Shopping Bag
    ---------------------------
${if (bag.isEmpty()) 
        "No Gifts stored" 
    else formatListBag(bag)}
    ---------------------------
     """.trimMargin(">")

//    fun findGift(index: Int): Gift? {
//        return if (isValidListIndex(index, bag)) {
//            bag[index]
//        } else null
//    }

    fun totalPrice(): Unit = println("Total Cost: " + bag.sumOf { gift -> gift.price })

    private fun formatListBag(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString(separator = "\n") { gift ->
//                "${gift.giftId}. " +
                bag.indexOf(gift).toString() + ". ${gift.title}  ${gift.price}"
//                totalPrice().toString()
            }
}