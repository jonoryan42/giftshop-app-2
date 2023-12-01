package controllers

import models.Gift
//import persistence.Serializer

import utils.Utilities.isValidListIndex
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

    fun findGift(index: Int): Gift? {
        return if (isValidListIndex(index, bag)) {
            bag[index]
        } else null
    }

    fun formatListBag(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString(separator = "\n") { gift ->
                bag.indexOf(gift).toString() + ": " + gift.toString() }
}