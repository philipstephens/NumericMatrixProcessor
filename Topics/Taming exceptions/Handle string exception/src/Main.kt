fun main() {
    var index = readLine()!!.toInt()
    var word = readLine()!!

    if (word != "") {
        if ((index < 0) || (index > word.lastIndex)) {
            println("There isn't such an element in the given string, please fix the index!")
        } else {
            println(word[index])
        }
    }
}
