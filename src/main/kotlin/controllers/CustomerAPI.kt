package controllers

import models.Customer
import java.util.ArrayList
import persistence.Serializer
//import utils.Utilities
import java.lang.Exception
import kotlin.jvm.Throws

class CustomerAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var customers = ArrayList<Customer>()

    private var lastId = 0

    private fun getId() = lastId++

    fun add(customer: Customer): Boolean {
        customer.customerId = getId()
        return customers.add(customer)
    }

    fun delete(id: Int) = customers.removeIf { customer -> customer.customerId == id }

    private fun listAllCustomers(): String =
        if (customers.isEmpty())
            "No Customers stored"
        else formatListString(customers)

    fun findCustomer(customerId:Int) = customers.find { customer ->
        customer.customerId == customerId
    }
    fun updateCustomer(customerId: Int, customer: Customer?): Boolean {
        val foundCustomer = findCustomer(customerId)
        if ((foundCustomer != null) && (customer != null)) {
            foundCustomer.customerId = customer.customerId
            foundCustomer.firstName = customer.firstName
            foundCustomer.surname = customer.surname
            foundCustomer.phone = customer.phone
            foundCustomer.email = customer.email
            return true
        }
        return false
    }

    fun numberOfCustomers(): Int = customers.size

    @Throws(Exception::class)
    fun load() {
        customers = serializer.read() as ArrayList<Customer>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(customers)
    }

    private fun formatListString(customersToFormat: List<Customer>): String =
        customersToFormat
            .joinToString("\n") { customer ->
                "ID. ${customer.customerId}. ${customer.getFullName()} / " +
                        "${customer.email} / ${customer.phone}"
            }
}