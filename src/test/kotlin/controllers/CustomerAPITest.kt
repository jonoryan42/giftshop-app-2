package controllers

import models.Customer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import java.io.File

class CustomerAPITest {
    private var steve: Customer? = null
    private var sean: Customer? = null
    private var leanne: Customer? = null
    private var buyers: CustomerAPI? = CustomerAPI(XMLSerializer(File("customers.xml")))
//    private var emptyBuy: CustomerAPI? = CustomerAPI(XMLSerializer(File("customers.xml")))

    @BeforeEach
    fun setup() {
        steve = Customer(
            0, "Steve", "Walsh",
            "steve@mail.ie", 3538944444, "sWalsh219"
        )
        sean = Customer(
            1, "Sean", "Penn",
            "spenn@mail.ie", 353853333, "sPenn2123"
        )
        leanne = Customer(
            2, "Leanne", "Whittaker",
            "lwhit@mail.ie", 353875455, "lWhitt7654"
        )

        buyers!!.add(steve!!)
        buyers!!.add(sean!!)
        buyers!!.add(leanne!!)
    }

    @AfterEach
    fun tearDown() {
        steve = null
        sean = null
        leanne = null
    }

    @Nested
    inner class AddCustomers {
        @Test
        fun `adding a Customer to a populated list adds to ArrayList`() {
            val newCustomer = Customer(
                6, "Jay", "Spearing",
                "jspear@mail.ie", 353467777, "jSpear75642"
            )
            assertTrue(buyers!!.add(newCustomer))
        }

        @Test
        fun `adding a Customer to an empty list adds to ArrayList`() {
            val newCustomer = Customer(
                6, "Jay", "Spearing",
                "jspear@mail.ie", 353467777, "jSpear75642"
            )
            assertTrue(buyers!!.add(newCustomer))
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            storingCustomers.store()

            val loadedCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            loadedCustomers.load()

            assertEquals(0, storingCustomers.numberOfCustomers())
            assertEquals(0, loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't loose data`() {
            val storingCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            storingCustomers.add(steve!!)
            storingCustomers.add(sean!!)
            storingCustomers.add(leanne!!)
            storingCustomers.store()

            val loadedCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            loadedCustomers.load()

            assertEquals(3, storingCustomers.numberOfCustomers())
            assertEquals(3, loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.findCustomer(0), loadedCustomers.findCustomer(0))
            assertEquals(storingCustomers.findCustomer(1), loadedCustomers.findCustomer(1))
            assertEquals(storingCustomers.findCustomer(2), loadedCustomers.findCustomer(2))
        }
    }
}