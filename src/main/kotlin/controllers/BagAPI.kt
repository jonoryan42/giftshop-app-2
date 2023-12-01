package controllers

import models.Gift
import utils.Utilities.isValidListIndex
import utils.Utilities.roundTwoDecimals
import kotlin.math.round

//import persistence.Serializer

//import kotlin.math.round

class BagAPI(/*serializerType: Serializer*/) {
//    private var serializer: Serializer = serializerType

    private var bag = ArrayList<Gift>()

//    private var lastBagId = 0
//
//    private fun getBagId() = lastBagId++

    fun add(gift: Gift): Boolean {
//        gift.bagId = getBagId()
        return bag.add(gift)
    }

//    fun addByIndex(indexToAdd: Int) :

//    fun addToBag(indexToAdd: Int): Gift? {
//        return if (isValidListIndex(indexToAdd, bag)) {
//            bag.add(indexToAdd)
//        } else null
//    }

//    fun deleteGift(indexToDelete: Int): Gift? {
//        return if (isValidListIndex(indexToDelete, bag)) {
//            bag.removeAt(indexToDelete)
//        } else null
//    } //Working on delete

    fun delete(id: String) = bag.removeIf { gift -> gift.giftId == id}


//    fun listAllGifts(): String =
//        if (bag.isEmpty())
//            "No Gifts stored"
//        else formatListBag(bag)

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

    fun totalPrice(): Unit = println("Total Cost: " + roundTwoDecimals(bag.sumOf { gift -> gift.price }))

    private fun formatListBag(giftsToFormat: List<Gift>): String {
       return  giftsToFormat
            .joinToString(separator = "\n") { gift ->
//                "${gift.giftId}. " +
           //     ${bagId}.
               // ${gift.bagId}
                  "ID. ${gift.giftId}. ${gift.title} /  ${gift.price}"
            }
    }
}