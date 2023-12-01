package controllers

import models.Gift
import utils.Utilities.roundTwoDecimals


class BagAPI {

    private var bag = ArrayList<Gift>()

    fun add(gift: Gift): Boolean {
        return bag.add(gift)
    }

    fun delete(id: Int) = bag.removeIf { gift -> gift.giftId == id}

    fun deleteAll() = bag.removeAll(bag)

    fun listShopping(): String = """
    ---------------------------    
    |       Shopping Bag      |
    ---------------------------
    
${if (bag.isEmpty()) 
        "No Gifts stored" 
    else formatListBag(bag)}
    ---------------------------
     """.trimMargin(">")

    fun numberOfGifts(): Int = bag.size

    fun totalPrice(): Unit = println("Total Cost: " + roundTwoDecimals(bag.sumOf { gift ->
        gift.price }  ) + "\n")

    private fun formatListBag(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString( "\n") { gift ->
                  "ID. ${gift.giftId}. ${gift.title} /  ${gift.price}"
            }
    }