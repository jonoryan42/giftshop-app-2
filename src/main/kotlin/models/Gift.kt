package models

data class Gift(
    var giftId: String,
    var title: String,
    var price: Double,
    var category: String,
    var stock: Int
)
