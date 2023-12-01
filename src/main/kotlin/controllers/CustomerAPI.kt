package controllers

import models.Customer
import java.util.ArrayList
import persistence.Serializer
import utils.Utilities

class CustomerAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var customers = ArrayList<Customer>()

    fun add(customer: Customer): Boolean {
        return customers.add(customer)
    }

    fun delete(id: Int) = customers.removeIf { customer -> customer.customerId == id }

    private fun listAllCustomers(): String =
        if (customers.isEmpty())
            "No Customers stored"
        else formatListString(customers)

    private fun formatListString(customersToFormat: List<Customer>): String =
        customersToFormat
            .joinToString("\n") { customer ->
                "ID. ${customer.customerId}. ${customer.getFullName()} / " +
                        "${customer.email} / ${customer.phone}"
            }
}