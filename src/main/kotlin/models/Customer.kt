package models

import java.util.*
import java.util.jar.Attributes

class Customer( //var customerId: Int,
                var customerId: Int = 0,
                var firstName: String,
                var surname: String,
                var email: String,
                var phone: Long) {
//    fun getFullName() = capital()
//    fun capital(): String =  firstName.replaceFirstChar { it ->
//        if(it.isLowerCase()) it.titlecase(Locale.getDefault())
//    else it.toString()
//    surname.replaceFirstChar { if(it.isLowerCase()) it.titlecase(Locale.getDefault())
//    else it.toString() }}


}