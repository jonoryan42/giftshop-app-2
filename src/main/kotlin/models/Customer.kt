package models

import java.util.*
import java.util.jar.Attributes

class Customer( var customerId: Int,
                var firstName: String,
                var surname: String,
                var email: String,
                var phone: Int) {
    fun getFullName() = capital()
    fun capital(): String =  firstName.replaceFirstChar { it ->
        if(it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString()
    surname.replaceFirstChar { if(it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString() }}
}