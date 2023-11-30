package controllers

import models.Gift
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
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

        @Test
        fun `adding a Gift to list adds to Arraylist`() {
            val newGift = Gift("Nintendo Switch White", 364.99, "Toy", 40)
            assertTrue(filled!!.add(newGift))
        }
    }