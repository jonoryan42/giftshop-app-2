package controllers

import models.Gift
import utils.Utilities.roundTwoDecimals

/**
 * Contains an arraylist and functions relating to a Shopping Bag.
 */
class BagAPI {

    /**
     * Variable used to house the arraylist for the Shopping Bag.
     *
     * @property Gift is the model used for the Shopping Bag arraylist.
     */
    private var bag = ArrayList<Gift>()

    /**
     * Function to add gifts to bag.
     */
    fun add(gift: Gift): Boolean {
        return bag.add(gift)
    }

    /**
     * Deletes gifts from bag depending on given gift ID.
     */
    fun delete(id: Int) = bag.removeIf { gift -> gift.giftId == id}

    /**
     * Deletes all items in the Shopping Bag.
     *
     * [removeAll] is used to delete all items of the arraylist at once.
     */
    fun deleteAll() = bag.removeAll(bag)

    /**
     * Returns a list of all gifts in the Shopping Bag.
     *
     * [trimMargin] adds '>' to each new line.
     */
    fun listShopping(): String = """
    ---------------------------    
    |       Shopping Bag      |
    ---------------------------
    
${if (bag.isEmpty()) 
        "No Gifts stored" 
    else formatListBag(bag)}
    ---------------------------
     """.trimMargin(">")

    /**
     * Returns number of Gifts.
     */
    fun numberOfGifts(): Int = bag.size

    /**
     * Returns the total Cost of gifts in the Shopping Bag.
     *
     * [sumOf] returns the sum of all involved gifts.
     *
     * [roundTwoDecimals] rounds down output to two decimal places.
     *
     * @property price represents the price of gift taken from Gift model.
     */
    fun totalPrice(): Unit = println("Total Cost: " + roundTwoDecimals(bag.sumOf { gift ->
        gift.price }  ) + "\n")

    /**
     * Returns gift information converted to String.
     */
    private fun formatListBag(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString( "\n") { gift ->
                  "ID. ${gift.giftId}. ${gift.title} /  ${gift.price}"
            }
    }