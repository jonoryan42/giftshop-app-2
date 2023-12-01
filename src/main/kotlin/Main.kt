import controllers.BagAPI
import controllers.GiftAPI
import controllers.CustomerAPI
import models.Gift
import models.Customer
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
private val giftAPI = GiftAPI(XMLSerializer(File("gifts.xml")))
//private val customerAPI = CustomerAPI(XMLSerializer(File("customers.xml")))
private val bagAPI = BagAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return readNextInt("""
        > ------------------------------------
        > |          GIFTSHOP APP             |
        > ------------------------------------
        > | 1) View Toys                      |
        > | 2) View Food                      |
        > | 3) View Jewellery                 |
        > | 4) Search                         |
        > -------------------------------------
        > | 5) View Your Shopping Bag         |
        > | 6) Create Account                 |
        > | 7) Make Payment                   |
        > | 8) Add Gift                       |
        > | 9) List Gifts                     |
        > | 50) Save                          |
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
            7 -> makePayment()
            8 -> addGift()
            9 -> listAllGifts()
            50 -> save()
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

fun viewBag()  {
    listBag()
    bagAPI.totalPrice()
    bagMenu()
}

fun createAccount() {

}

fun makePayment() {

}

fun save() {
    try {
        giftAPI.store()
        //customerAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        giftAPI.load()
        //customerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
    fun exitApp() {
        println("App ended.")
        exit(0)
    }

fun addGift() {
    val giftId = readNextLine("enter id")
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
    val indexToAdd = readNextLine("Enter ID of product to add to Bag: ")
    val giftToAdd = giftAPI.findGift(indexToAdd)
    if (giftToAdd != null) {
        bagAPI.add(giftToAdd)
        println("${giftToAdd.title} added to Bag. \n")
    } else {
        logger.info("No Products Match this ID.")
    }
    listAllGifts()
}

fun listIntro() : Int = readNextInt("Select: 1. Add Product To Bag. 0. Exit \n >>")

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
        when (val option = bagIntro() ) {
            1 -> removeFromBag()
            0 -> runMenu()
            else -> logger.info {"Invalid option entered: $option" }
        }
    } while (true)
}
fun bagIntro() : Int = readNextInt("Select: 1. Remove Product From Bag. 0. Exit \n >>")


fun removeFromBag() {
    val indexToDelete = readNextLine("Enter the ID of Product to remove from Bag: ")
    val idForDelete = giftAPI.findGift(indexToDelete)
    if (indexToDelete != null) {
        bagAPI.delete((indexToDelete))
        println("${idForDelete!!.title} was removed from Bag. \n")
    }
    viewBag()
}
//Unfortunately, selecting ID of product for deletion from the shopping bag
//will remove duplicates as well

fun listBag() = println( bagAPI.listShopping())

