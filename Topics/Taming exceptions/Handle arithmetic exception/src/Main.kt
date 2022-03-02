import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var x = scanner.nextInt()
    var y = scanner.nextInt()

    if ( y == 0 ) {
        println("Division by zero, please fix the second argument!")
    } else {
        println( x/y )
    }
}
