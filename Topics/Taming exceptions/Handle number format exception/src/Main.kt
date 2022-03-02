fun parseCardNumber(cardNumber: String): Long {
    val creditCardStr = cardNumber
    val regex = Regex("[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}")
    var x2 = ""

    if (!regex.matches(creditCardStr)) {
        throw(Exception())
    }

    for(i in creditCardStr.indices) {
        if (creditCardStr[i] in '0'..'9') {
            x2 += creditCardStr[i]
        }
    }

    return x2.toLong()
}
