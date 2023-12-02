package controllers

import models.Gift
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import utils.Utilities.formatListString
import java.io.File


class GiftTest {
    private var winnie: Gift? = null
    private var nestle: Gift? = null
    private var emerald: Gift? = null
    private var filled: GiftAPI? = GiftAPI(XMLSerializer(File("gifts2.xml")))
    private var empty: GiftAPI? = GiftAPI(XMLSerializer(File("gifts2.xml")))
    private var filledBag: BagAPI? = BagAPI()

    @BeforeEach
    fun setup() {
        winnie = Gift(0,"Winnie The Pooh Teddy", 9.99, "Toy", 50)
        nestle = Gift(1,"Nestle Chocolate Box", 14.99, "Food", 60)
        emerald = Gift(2,"Emerald Dangle Earrings", 61.99, "Jewellery", 50)

        filled!!.add(winnie!!)
        filled!!.add(nestle!!)
        filled!!.add(emerald!!)

        filledBag!!.add(winnie!!)
        filledBag!!.add(nestle!!)
        filledBag!!.add(emerald!!)
    }

    @AfterEach
    fun tearDown() {
        winnie = null
        nestle = null
        emerald = null
        filled = null
        empty = null
        filledBag = null
    }

    @Nested
    inner class addGifts {
        @Test
        fun `adding a Gift to list adds to Arraylist`() {
            val newGift = Gift(3,"Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(filled!!.add(newGift))
        }

        @Test
        fun `adding a Gift to an empty list adds to Arraylist`() {
            val newGift = Gift(4,"Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(empty!!.add(newGift))
        }
    }
@Nested
inner class addingPrices {
    @Test
    fun `adding prices together gives total price`() {
        val shop: Double? = filled!!.getPrice(winnie)?.plus(filled!!.getPrice(nestle)!!)
        println(shop)
    }

    @Test
    fun `adding all product prices together for new arraylist will give total`() {
        var gifts = ArrayList<Gift>()
        val nintendo = Gift(0,"Nintendo Switch White", 364.99, "Toy", 40)
        val control = Gift(1,"Nintendo Gaming Controller", 25.00, "Toy", 30)
        val wispa = Gift(2,"Wispa Bar", 0.99, "Food", 100)

        gifts.add(nintendo)
        gifts.add(control)
        gifts.add(wispa)

        val prices = gifts.sumOf { gift -> gift.price }
        return println(prices)
    }
}
    @Test
    fun `selecting product should copy it from one arraylist into another`() {
        var gifts = ArrayList<Gift>()
        val nintendo = Gift(0,"Nintendo Switch White", 364.99, "Toy", 40)
        val control = Gift(1,"Nintendo Gaming Controller", 25.00, "Toy", 30)
        val wispa = Gift(2,"Wispa Bar", 0.99, "Food", 100)

        gifts.add(nintendo)
        gifts.add(control)
        gifts.add(wispa)

        var bag = ArrayList<Gift>()
        bag.add(nintendo)
        bag.add(control)

        println(formatListString(bag) + "\n")
        println(formatListString(gifts) + "\n")
    }

    @Test
    fun `deleting all gifts from arraylist should leave arraylist empty`() {
        filledBag!!.deleteAll()
        assertEquals(0, filledBag!!.numberOfGifts())
    }

}