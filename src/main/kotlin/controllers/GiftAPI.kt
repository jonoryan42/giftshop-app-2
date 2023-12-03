package controllers

import models.Gift
import persistence.Serializer

/**
 * This class is for the creation of an Arraylist of type Gift and the functions associated with it.
 *
 * The gifts Arraylist and function can then be used in my Main file.
 *
 * @param serializerType is of the [Serializer] interface.
 * @constructor creates a GiftAPI.
 */
class GiftAPI(serializerType: Serializer) {
    /**@property serializer variable is created to link to Serializer interface.
     */
    private var serializer: Serializer = serializerType

    /**@property gifts is the Arraylist created of the Gift model.
     */
    private var gifts = ArrayList<Gift>()

    /**
     * Adds gifts to the [gifts] arraylist using [.add].
     *
     * Gift must be of type [Gift] to be added.
     *
     * @property gift the type of object to be added.
     */
    fun add(gift: Gift): Boolean {
        return gifts.add(gift)
    }

    /**
     * Function to delete objects from the arraylist using [.removeif].
     *
     * Takes an ID for a gift and will remove the gift from arraylist if ID is valid.
     *
     * @property id used to match up with ID of gift.
     */
    fun delete(id: Int) = gifts.removeIf { gift -> gift.giftId == id}

    /**
     * Function to show list of all Gifts in the arraylist using [formatListString]
     *
     * Returns message if arraylist is empty.
     */
    fun listAllGifts(): String =
        if (gifts.isEmpty())
            "No Gifts stored"
        else formatListString(gifts)

    /**
     * Function to allow user to display list of Gifts depending on given category.
     *
     * Uses [.filter] to find all gifts matching the given category.
     *
     * @property category is used to match up with category of Gifts.
     * [numberOfGiftsByCategory] will display the number of Gifts for the entered category.
     */
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

    /**
     * Function to determine the number of gifts in the arraylist.
     *
     * [.size] returns the number of objects.
     */
    fun numberOfGifts(): Int = gifts.size

    /**
     * Returns the number of Gifts in arraylist depending on entered category.
     *
     * [count] counts the number of gifts.
     *
     * @property category used to match up with category of gift.
     */
    fun numberOfGiftsByCategory(category: String): Int = gifts.count {gift: Gift ->
        gift.category == category}

    /**
     * Searches for a gift in list depending on entered String.
     *
     * [contains] checks if title includes the String entered.
     *
     * @property searchString used to match up with name of gift.
     */
    fun searchByTitle(searchString: String) =
        formatListString(
            gifts.filter { gift -> gift.title.contains(searchString, ignoreCase = true) })

    /**
     * Finds a gift in list depending on entered gift ID.
     *
     * [find] searches the arraylist to find single gift that matches entered ID.
     *
     * @property giftId ID given to all gifts.
     */
    fun findGift(giftId : Int) =  gifts.find{ gift -> gift.giftId == giftId }

    /**
     * Used to load gifts from a file.
     *
     * [.read] is taken from [Serializer] interface and is used to read files.
     */
    @Throws(Exception::class)
    fun load() {
        gifts = serializer.read() as ArrayList<Gift>
    }

    /**
     * Used to save gifts to a file.
     *
     * [.write] is taken from [Serializer] interface and is used to write to files.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(gifts)
    }

    /**
     * Formats the arraylist into String.
     *
     * [.join.toString] Formats output to String.
     *
     * @property giftsToFormat is of the Gift arraylist and is converted to String.
     */
    private fun formatListString(giftsToFormat: List<Gift>): String =
        giftsToFormat
            .joinToString( "\n") { gift -> "ID. ${gift.giftId}. " +
                    "${gift.title} / Price: ${gift.price} / In Stock: ${gift.stock} / ${gift.category}"}

    }




