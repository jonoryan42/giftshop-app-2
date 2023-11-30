package controllers

import models.Gift
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import utils.Utilities.formatListString
import java.io.File


class GiftAPITest {
    private var winnie: Gift? = null
    private var nestle: Gift? = null
    private var emerald: Gift? = null
    private var filled: GiftAPI? = GiftAPI(XMLSerializer(File("gifts2.xml")))
    private var empty: GiftAPI? = GiftAPI(XMLSerializer(File("gifts2.xml")))

    @BeforeEach
    fun setup() {
        winnie = Gift("Winnie The Pooh Teddy", 9.99, "Toy", 50)
        nestle = Gift("Nestle Chocolate Box", 14.99, "Food", 60)
        emerald = Gift("Emerald Dangle Earrings", 61.99, "Jewellery", 50)

        filled!!.add(winnie!!)
        filled!!.add(nestle!!)
        filled!!.add(emerald!!)
    }

    @AfterEach
    fun tearDown() {
        winnie = null
        nestle = null
        emerald = null
        filled = null
        empty = null
    }

    @Nested
    inner class addGifts {
        @Test
        fun `adding a Gift to list adds to Arraylist`() {
            val newGift = Gift("Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(filled!!.add(newGift))
        }

        @Test
        fun `adding a Gift to an empty list adds to Arraylist`() {
            val newGift = Gift("Nintendo Switch White", 364.99, "Toy", 40)
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

    //@Test
//    fun `adding prices of all Arraylist objects will give total price`() {
//        println(filled!!.)
//}

//    @Test
//    fun <T> Iterable<T>.sumOf(selector: Double) {
//        var gifts = ArrayList<Gift>()
//        val nintendo = Gift("Nintendo Switch White", 364.99, "Toy", 40)
//        val control = Gift("Nintendo Gaming Controller", 25.00, "Toy", 30)
//        val wispa = Gift("Wispa Bar", 0.99, "Food", 100)
//
//        gifts.add(nintendo)
//        gifts.add(control)
//        gifts.add(wispa)
//
//        val prices  = gifts.forEach{gift -> gift.price }
//        return println(sumOf(prices as Double))
//    }

    @Test
    fun `adding all product prices together for new arraylist will give total`() {
        var gifts = ArrayList<Gift>()
        val nintendo = Gift("Nintendo Switch White", 364.99, "Toy", 40)
        val control = Gift("Nintendo Gaming Controller", 25.00, "Toy", 30)
        val wispa = Gift("Wispa Bar", 0.99, "Food", 100)

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
        val nintendo = Gift("Nintendo Switch White", 364.99, "Toy", 40)
        val control = Gift("Nintendo Gaming Controller", 25.00, "Toy", 30)
        val wispa = Gift("Wispa Bar", 0.99, "Food", 100)

        gifts.add(nintendo)
        gifts.add(control)
        gifts.add(wispa)

        var bag = ArrayList<Gift>()
        bag.add(nintendo)
        println(formatListString(bag) + "\n")
        println(formatListString(gifts) + "\n")
    }

}