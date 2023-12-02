import controllers.BagAPI
import controllers.GiftAPI
import controllers.CustomerAPI
import models.Gift
import models.Customer
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readNextLong
import java.io.File
import java.lang.System.exit
import java.util.*


private val logger = KotlinLogging.logger {}
private val giftAPI = GiftAPI(XMLSerializer(File("gifts.xml")))
private val customerAPI = CustomerAPI(XMLSerializer(File("customers.xml")))
private val bagAPI = BagAPI()

private var logged = " "

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return readNextInt("""
        > ------------------------------------
        >        
        >           GIFTSHOP APP    
        >                           $logged       
        > ------------------------------------
        > | 1) View Toys                      |
        > | 2) View Food                      |
        > | 3) View Jewellery                 |
        > | 4) Search                         |
        > -------------------------------------
        > | 5) View Your Shopping Bag         |
        > | 6) Create Account                 |
        > | 7) Log-In                         |
        > | 8) Checkout                       |
        > | 9) Add Gift                       |
        > | 10) List Gifts                    |
        > -------------------------------------
        > | 0) Exit                           |
        > -------------------------------------
        >  >> """.trimMargin(">"))

}

fun runMenu() {
    load()
    do {
        val option = mainMenu()
        when (option) {
            1 -> viewToys()
            2 -> viewFood()
            3 -> viewJewel()
            4 -> search()
            5 -> viewBag()
            6 -> createAccount()
            7 -> login()
            8 -> checkout()
            9 -> addGift()
            10 -> listAllGifts()
            0 -> exitApp()
            else -> logger.info ("Invalid option entered: ${option}")
        }
    } while (true)
}

fun viewToys() {

}

fun viewFood() {

}

fun viewJewel() {

}

fun search() {

}

fun viewBag() {
    if (bagAPI.numberOfGifts() == 0) {
        println("No Gifts Stored in Bag.")
    }
    else {
        listBag()
        bagAPI.totalPrice()
        bagMenu()
    }
}

fun createAccount() {
    println("Please enter these details.")
    createAccountMenu()
}

fun checkout() {
      if (logged !== "Logged In") {
          println("You must Log-In first.")
      }
          else {
          if (bagAPI.numberOfGifts() == 0) {
              println("\nNo Gifts in Bag")
          } else {
              listBag()
              bagAPI.totalPrice()

              val next = readNextInt(
                  "Would you like to Checkout? \n " +
                          "Select:  1. Checkout. 0. Back To Menu. \n >> "
              )
              if (next == 1) {
                  bagAPI.deleteAll()
                  println("Thank you for your purchase!")
                  runMenu()
              } else {
                  println("Did not Checkout.")
              }
          }
      }
    }

fun login() {

    if(logged == "Logged In") {
        println("You're already logged in.")
    }
    else {
        val enterEmail = readNextLine("Email Address: >> ")
        val account = customerAPI.validEmail(enterEmail)
        if (enterEmail != account?.email) {
            logger.info("Invalid Email entered.")
        } else {
            val enterPwd = readNextLine("Password: >> ")
            val secure = customerAPI.validPwd(enterPwd)
            if (enterPwd != secure?.password) {
                logger.info("Invalid Password entered.")
            } else {
                println("You have successfully logged in, ${secure.firstName}.")
                logged = "Logged In"
            }
        }
    }
}

fun save() {
    try {
//        giftAPI.store()
        customerAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        giftAPI.load()
        customerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
    fun exitApp() {
        println("App ended.")
        exit(0)
    }

fun addGift() {
    val giftId = readNextInt("enter id")
    val title = readNextLine("enter title: ")
    val price = readNextDouble("enter price: ")
    val category = readNextLine("enter category: ")
    val stock = readNextInt("enter stock: ")
    val isAdded = giftAPI.add(Gift(giftId, title, price, category, stock))
    if (isAdded) {
        println("Gift Added")
    } else {
        logger.info {"Add Failed"}
    }
}

fun listAllGifts() {
    println(giftAPI.listAllGifts() + "\n")
    listMenu()
    }

fun addToBag() {
    val indexToAdd = readNextInt("Enter ID of product to add to Bag: \n >> ")
    val giftToAdd = giftAPI.findGift(indexToAdd)
    if (giftToAdd != null) {
        bagAPI.add(giftToAdd)
        println("${giftToAdd.title} added to Bag. \n")
    } else {
        logger.info("No Products Match this ID.")
    }
    listAllGifts()
}

fun listIntro() : Int = readNextInt("Select: 1. Add Product To Bag. 0. Exit \n >> ")

fun listMenu() {
    do {
        when (val option = listIntro() ) {
            1 -> addToBag()
            0 -> runMenu()
            else -> logger.info {"Invalid option entered: $option" }
        }
    } while (true)
}

fun bagMenu() {
        do {
            when (val option = bagIntro()) {
                1 -> removeFromBag()
                2 -> removeAllFromBag()
                3 -> checkout()
                0 -> runMenu()
                else -> logger.info { "Invalid option entered: $option" }
            }
        } while (true)
}
fun bagIntro() : Int = readNextInt("Select: \n1. Remove Product from Bag. " +
        "\n2. Remove All Products from Bag. \n3. Checkout. \n0. Exit \n >> ")


fun removeFromBag() {
    val indexToDelete = readNextInt("Enter the ID of Product to remove from Bag: \n >> ")
    val idForDelete = giftAPI.findGift(indexToDelete)
    if (indexToDelete != null) {
        bagAPI.delete((indexToDelete))
        println("${idForDelete!!.title} was removed from Bag. \n")
    }
    viewBag()
}
//Unfortunately, selecting ID of product for deletion from the shopping bag
//will remove duplicates as well.

fun removeAllFromBag() {
    if (bagAPI.numberOfGifts() != 0 ) {
        bagAPI.deleteAll()
        println("All Products were removed from Bag. \n")
    }
    else {
        println("There are no stored Products. \n")
    }
    viewBag()
}

fun listBag() = println( bagAPI.listShopping())
//printing shopping bag before getting total

fun createAccountMenu() {
    val firstName = readNextLine("First Name: >> ")
    val surname = readNextLine("Surname: >> ")
    val email = readNextLine("Email Address: >> ")
    if (email.contains("@") && email.contains(".")) {
        val phone = readNextLong("Phone No.: >> ")
        val password = readNextLine("Please enter a strong Password: >> ")
        if (password.length < 8) {
            logger.info("Invalid. Password must be longer than eight characters.")

        } else {
            val isAdded = customerAPI.add(
                Customer(
                    firstName = firstName, surname = surname,
                    email = email, phone = phone, password = password
                )
            )
            if (isAdded) {
                println("Account Created")
                save()
            }
        } } else {
            logger.info("Account could not be created.")
        }
}
//creating Customer account
