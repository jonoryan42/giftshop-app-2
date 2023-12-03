package controllers

import models.Gift
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
//import persistence.CborSerializer
import utils.Utilities.formatListString
import java.io.File


class GiftTest {
    private var winnie: Gift? = null
    private var nestle: Gift? = null
    private var emerald: Gift? = null

        private var filled: GiftAPI? = GiftAPI(XMLSerializer(File("gifts.xml")))
    private var empty: GiftAPI? = GiftAPI(XMLSerializer(File("gifts.xml")))
//    private var filled: GiftAPI? = GiftAPI(CborSerializer(File("gifts.cbor")))
//    private var empty: GiftAPI? = GiftAPI(CborSerializer(File("gifts.cbor")))

    private var filledBag: BagAPI? = BagAPI()

    @BeforeEach
    fun setup() {
        winnie = Gift(0, "Winnie The Pooh Teddy", 9.99, "Toy", 50)
        nestle = Gift(1, "Nestle Chocolate Box", 14.99, "Food", 60)
        emerald = Gift(2, "Emerald Dangle Earrings", 61.99, "Jewellery", 50)

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
            val newGift = Gift(3, "Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(filled!!.add(newGift))
        }

        @Test
        fun `adding a Gift to an empty list adds to Arraylist`() {
            val newGift = Gift(4, "Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(empty!!.add(newGift))
        }
    }

    @Nested
    inner class listingGifts {
        @Test
        fun `listByCategory returns No Gifts when ArrayList is empty`() {
            assertEquals(0, empty!!.numberOfGifts())
            assertTrue(
                empty!!.listByCategory("Toy").lowercase().contains("no gifts")
            )
        }

        @Test
        fun `listByCategory returns no Gifts when no Gifts of that category exist`() {
            assertEquals(3, filled!!.numberOfGifts())
            val nintendo = Gift(3, "Nintendo Switch White", 364.99, "Toy", 40)
            filled!!.add(nintendo)
            filled!!.delete(2)
            val category2String = filled!!.listByCategory("Jewellery").lowercase()
            assertTrue(category2String.contains("no gifts"))
            assertTrue(category2String.contains("jewellery"))
            println(category2String)
        }

        @Test
        fun `listByCategory returns all gifts that match that category when gifts of that category exist`() {
            assertEquals(3, filled!!.numberOfGifts())
            val nintendo = Gift(3, "Nintendo Switch White", 364.99, "Toy", 40)
            filled!!.add(nintendo)
            val category1String = filled!!.listByCategory("Toy").lowercase()
            assertTrue(category1String.contains("toy"))
            assertTrue(category1String.contains("nintendo"))
            assertTrue(category1String.contains("winnie"))
            assertFalse(category1String.contains("nestle"))
            assertFalse(category1String.contains("emerald"))

            println(category1String)

            val category3String = filled!!.listByCategory("Jewellery").lowercase()
            assertTrue(category3String.contains("emerald"))
            assertFalse(category3String.contains("winnie"))

            println(category3String)
        }
    }

    @Nested
    inner class searching {
        @Test
        fun `searching using String should bring up no gifts if String does mot match a gift title`() {
            val searchResult = filled!!.searchByTitle("wispa")
            assertFalse(searchResult.contains(winnie!!.title))
        }

        @Test
        fun `searching using String should bring up gifts of a similiar name`() {
            val searchResults = filled!!.searchByTitle("winnie")
            val searchResults2 = filled!!.searchByTitle("emerald")
            assertTrue(searchResults.contains(winnie!!.title))
            assertTrue(searchResults2.contains(emerald!!.title))
            println(searchResults)
        }
    }

    @Nested
    inner class shoppingBagTests {
        @Test
        fun `selecting product should copy it from one arraylist into another`() {
            var gifts = ArrayList<Gift>()
            val nintendo = Gift(0, "Nintendo Switch White", 364.99, "Toy", 40)
            val control = Gift(1, "Nintendo Gaming Controller", 25.00, "Toy", 30)
            val wispa = Gift(2, "Wispa Bar", 0.99, "Food", 100)

            gifts.add(nintendo)
            gifts.add(control)
            gifts.add(wispa)

            var bag = ArrayList<Gift>()
            bag.add(nintendo)
            bag.add(control)

            println(formatListString(bag) + "\n")
            println(formatListString(gifts) + "\n")

            assertTrue(bag.contains(nintendo))
        }
        //Copying from gift menu to shopping bag.

        @Test
        fun `deleting all gifts from arraylist should leave arraylist empty`() {
            filledBag!!.deleteAll()
            assertEquals(0, filledBag!!.numberOfGifts())
        }
        //Removing from bag.

        @Test
        fun `adding all product prices together for new arraylist will give total`() {
            var gifts = ArrayList<Gift>()
            val nintendo = Gift(0, "Nintendo Switch White", 364.99, "Toy", 40)
            val control = Gift(1, "Nintendo Gaming Controller", 25.00, "Toy", 30)
            val wispa = Gift(2, "Wispa Bar", 0.99, "Food", 100)

            gifts.add(nintendo)
            gifts.add(control)
            gifts.add(wispa)

            val prices = gifts.sumOf { gift -> gift.price }
            return println(prices)
        }

    }
    @Nested
    inner class persistence {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingGifts = GiftAPI(XMLSerializer(File("gifts.xml")))
            storingGifts.store()

            val loadedGifts = GiftAPI(XMLSerializer(File("gifts.xml")))
            loadedGifts.load()

            assertEquals(0, storingGifts.numberOfGifts())
            assertEquals(0, loadedGifts.numberOfGifts())
            assertEquals(storingGifts.numberOfGifts(), loadedGifts.numberOfGifts())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {
            val storingGifts = GiftAPI(XMLSerializer(File("gifts.xml")))
            storingGifts.add(winnie!!)
            storingGifts.add(nestle!!)
            storingGifts.add(emerald!!)
            storingGifts.store()

            val loadedGifts = GiftAPI(XMLSerializer(File("gifts.xml")))
            loadedGifts.load()

            assertEquals(3, storingGifts.numberOfGifts())
            assertEquals(3, loadedGifts.numberOfGifts())
            assertEquals(storingGifts.numberOfGifts(), loadedGifts.numberOfGifts())
            assertEquals(storingGifts.findGift(0), loadedGifts.findGift(0))
            assertEquals(storingGifts.findGift(1), loadedGifts.findGift(1))
            assertEquals(storingGifts.findGift(2), loadedGifts.findGift(2))

        }
    }
    }
//        @Test
//        fun `saving and loading an empty collection in CBOR doesn't crash app`() {
//            val storingGifts = GiftAPI(CborSerializer(File("gifts.cbor")))
//            storingGifts.store()
//
//            val loadedGifts = GiftAPI(CborSerializer(File("gifts.cbor")))
//            loadedGifts.load()
//
//            assertEquals(0, storingGifts.numberOfGifts())
//            assertEquals(0, loadedGifts.numberOfGifts())
//            assertEquals(storingGifts.numberOfGifts(), loadedGifts.numberOfGifts())
//        }
//    }
