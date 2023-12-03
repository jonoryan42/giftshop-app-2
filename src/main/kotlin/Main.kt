import controllers.BagAPI
import controllers.GiftAPI
import controllers.CustomerAPI
import models.Customer
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readNextLong
import java.io.File
import java.lang.System.exit

/**
 * Allows use of KotlinLogging.
 */
private val logger = KotlinLogging.logger {}

/**
 * Creates GiftAPI object.
 *
 * @property XMLSerializer allows to save and load to an xml file.
 */
private val giftAPI = GiftAPI(XMLSerializer(File("gifts.xml")))

/**
 * Creates CustomerAPI object.
 */
private val customerAPI = CustomerAPI(XMLSerializer(File("customers.xml")))

/**
 * Creates BagAPI object.
 */
private val bagAPI = BagAPI()

/**
 * Variable for text on menu.
 */
private var logged = " "

/**
 * Main Method.
 *
 * [runMenu] Runs menu function.
 */
fun main(args: Array<String>) {
    runMenu()
}

/**
 * Interface for Main Menu.
 *
 * [readNextInt] prompts user to enter an Int.
 */
fun mainMenu() : Int {
    return readNextInt("""
        > ------------------------------------
        >        
        >           GIFTSHOP APP    
        >                           $logged       
        > ------------------------------------
        > | 1) All Gifts                      |
        > | 2) Gift Menu                      |
        > | 3) Search                         |
        > -------------------------------------
        > | 4) View Your Shopping Bag         |
        > | 5) Create Account                 |
        > | 6) Log-In                         |
        > | 7) Checkout                       |
        > -------------------------------------
        > | 0) Exit                           |
        > -------------------------------------
        >  >> """.trimMargin(">"))

}
//'logged' variable is placed in top right of menu to display that user has logged in.

/**
 * Represents options to be selected on Menu.
 *
 * Options are selected by Int and include various Main functions.
 */
fun runMenu() {
    load()
    do {
        val option = mainMenu()
        when (option) {
            1 -> listAllGifts()
            2 -> giftMenu()
            3 -> search()
            4 -> viewBag()
            5 -> createAccount()
            6 -> login()
            7 -> checkout()
            0 -> exitApp()
            else -> logger.info ("Invalid option entered: ${option}")
        }
    } while (true)
}

/**
 * Function for displaying different categories of gift listing.
 *
 * This menu takes an Int and returns listing based on user selection.
 *
 * [logger] Shows logging message.
 */
fun giftMenu() {
    if (giftAPI.numberOfGifts() > 0) {
        val option = readNextInt(
            """Pick which Category you wish to view.
                >-------------------
                > | 1) Toys        |
                > | 2) Food        |
                > | 3) Jewellery   |
                > | 0) Exit        |
                > ------------------
           >> """.trimMargin(">")
        )

        when (option) {
            1 -> toyList()
            2 -> foodList()
            3 -> jewelList()
            0 -> runMenu()

            else -> logger.info { "Invalid option entered: $option" }
        }
    } else {
            logger.info { "No Gifts Stored" }
        }
}

/**
 * Function for displaying list of Gifts of Category: Toy.
 */
fun toyList() {
    println(giftAPI.listByCategory("Toy"))
    listMenu()

}

/**
 * Function for displaying list of Gifts of Category: Food.
 */
fun foodList() {
    println(giftAPI.listByCategory("Food"))
    listMenu()
}

/**
 * Function for displaying list of Gifts of Category: Jewellery.
 */
fun jewelList() {
    println(giftAPI.listByCategory("Jewellery"))
    listMenu()
}

/**
 * Function that allows user to search for a Gift.
 *
 * Will return whether searched String matches a Gift name or not.
 */
fun search() {
    val searchTitle = readNextLine("Enter Gift name to search by: \n>> ")
    val searchResults = giftAPI.searchByTitle(searchTitle)
    if(searchResults.isEmpty()) {
        println("There's no gift matching this search.")
    } else {
        println(searchResults)
        listMenu()
    }
}

/**
 * Displays gifts added to the Shopping Bag.
 */
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

/**
 * Allows User to Create an Account.
 */
fun createAccount() {
    println("Please enter these details.")
    createAccountMenu()
}

/**
 * Allows user to Checkout.
 *
 * Items are removed from bag after checkout.
 */
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

/**
 * Allows user to log in to their account.
 *
 * Checks validation of user and password.
 */
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
//Used the variable 'logged' as a way of signaling that user has logged in.

/**
 * Function to save entered Customer information.
 */
fun save() {
    try {
//        giftAPI.store()
        customerAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Function to load Customer and Gift information.
 */
fun load() {
    try {
        giftAPI.load()
        customerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Function to exit the app.
 */
    fun exitApp() {
        println("App ended.")
        exit(0)
    }

//fun addGift() {
//    val giftId = readNextInt("enter id")
//    val title = readNextLine("enter title: ")
//    val price = readNextDouble("enter price: ")
//    val category = readNextLine("enter category: ")
//    val stock = readNextInt("enter stock: ")
//    val isAdded = giftAPI.add(Gift(giftId, title, price, category, stock))
//    if (isAdded) {
//        println("Gift Added")
//    } else {
//        logger.info {"Add Failed"}
//    }
//}

/**
 * Returns a list of All Gifts.
 */
fun listAllGifts() {
    println("\nAll Gifts \n\n" + giftAPI.listAllGifts() + "\n")
    listMenu()
    }

/**
 * Allows user to add to shopping bag.
 */
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
//Unfortunately after you add to bag from a category list, it takes you to the All Gifts list.

/**
 * Displays text and takes an Int for menu options.
 */
fun listIntro() : Int = readNextInt("Select: \n1. Add Product To Bag.\n2. Toys\n3. Food" +
        "\n4. Jewellery\n0. Exit \n >> ")

/**
 * Another Menu that allows user to traverse to other Menu sections.
 */
fun listMenu() {
    do {
        when (val option = listIntro() ) {
            1 -> addToBag()
            2 -> toyList()
            3 -> foodList()
            4 -> jewelList()
            0 -> runMenu()
            else -> logger.info {"Invalid option entered: $option" }
        }
    } while (true)
}

/**
 * Options for the Shopping Bag Menu.
 */
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

/**
 * Text displayed on Shopping Bag Menu.
 */
fun bagIntro() : Int = readNextInt("Select: \n1. Remove Product from Bag. " +
        "\n2. Remove All Products from Bag. \n3. Checkout. \n0. Exit \n >> ")

/**
 * Allows user to remove items from Bag.
 */
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

/**
 * Option to Remove all gifts from Bag.
 */
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

/**
 * Displays all gifts in the shopping bag.
 */
fun listBag() = println(bagAPI.listShopping())
//printing shopping bag before getting total

/**
 * Menu used for creating Customer Account.
 */
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
