package controllers

import models.Customer
import persistence.Serializer
import java.lang.Exception
import java.util.*
import kotlin.jvm.Throws

/**
 *Creates a CustomerAPI used for creating customer objects and related functions.
 *
 * @param serializerType links it to the [Serializer] interface.
 * @constructor Creates a CustomerAPI.
 */
class CustomerAPI(serializerType: Serializer) {

    /**
     * Variable created to link with the [Serializer] interface.
     */
    private var serializer: Serializer = serializerType

    /**
     * Variable used to house arraylist for Customers.
     *
     * @property Customer is of the Customer model.
     */
    private var customers = ArrayList<Customer>()

    /**
     * Variable for a customer ID set as 0.
     */
    private var lastId = 0

    /**
     * Function to obtain the ID of a customer, which is set as lastid increasing by 1 incrementally.
     */
    private fun getId() = lastId++

    /**
     * Function to add customers to the arraylist.
     *
     * The customer ID is also set here using [getId].
     *
     * @property customer is of the Customer model.
     */
    fun add(customer: Customer): Boolean {
        customer.customerId = getId()
        return customers.add(customer)
    }

//    private fun delete(id: Int) = customers.removeIf { customer ->
//        customer.customerId == id }

//    private fun listAllCustomers(): String =
//        if (customers.isEmpty())
//            "No Customers stored"
//        else formatListString(customers)

    /**
     * Finds customers in the arraylist depending on the entered Customer ID.
     *
     * [find] searches through list and finds single Customer that matches given ID.
     *
     * @property customerId represents the ID of the Customer.
     */
    fun findCustomer(customerId: Int) = customers.find { customer ->
        customer.customerId == customerId
    }

    /**
     * Finds customers in the arraylist depending on the given email entered.
     *
     * @property email must match the email belonging to a Customer in order to be found.
     */
    fun validEmail(email: String): Customer? = customers.find { customer ->
        customer.email == email
    }
    //Searches for Customer by their email

    /**
     * Finds customers in the arraylist depending on the given password.
     *
     * @property password must be the same as the password applied to the Customer in order for the
     * customer to be found.
     */
    fun validPwd(password: String): Customer? = customers.find { customer ->
        customer.password == password
    }
    //Searches for Customer by their password to validate

//    fun updateCustomer(customerId: Int, customer: Customer): Boolean {
//        val foundCustomer = findCustomer(customerId)
//        if ((foundCustomer != null) && (customer != null)) {
//            foundCustomer.customerId = customer.customerId
//            foundCustomer.firstName = customer.firstName
//            foundCustomer.surname = customer.surname
//            foundCustomer.phone = customer.phone
//            foundCustomer.email = customer.email
//            foundCustomer.password = customer.password
//            return true
//        }
//        return false
//    }

    /**
     * Returns the number of customers in the arraylist.
     */
    fun numberOfCustomers(): Int = customers.size

    /**
     * Used to load customer arraylist.
     */
    @Throws(Exception::class)
    fun load() {
        customers = serializer.read() as ArrayList<Customer>
    }

    /**
     * Used to save customer arraylist.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(customers)
    }
//    private fun formatListString(customersToFormat: List<Customer>): String =
//        customersToFormat
//            .joinToString("\n") { customer ->
//                "ID. ${customer.customerId}. ${customer.getFullName()} / " +
//                        "${customer.email} / ${customer.phone}"
//            }
}