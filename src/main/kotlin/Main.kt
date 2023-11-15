fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
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
        > -------------------------------------
        > | 0) Exit                           |
        > -------------------------------------
        
    """.trimMargin(">"))

}

fun runMenu() {
    load()
    do {
        val option = mainMenu()
        when (option) {
            1 -> viewToys()
            2 -> viewfood()
            3 -> viewJewel()
            4 -> search()
            5 -> viewBag()
            6 -> createAccount()
            7 -> makePayment()
            0 -> exitApp()
            else -> logger.info ("Invalid option entered: ${option}")
        }
    } while (true)
    save()
}

fun exitApp() {
    println("App ended.")
    exit(0)
}