package processor

import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

val scanner = Scanner(System.`in`)

fun main() {
    var choice = -1

    while (choice != 0) {
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("6. Inverse Matrix")
        println("0. Exit")
        print("Your choice: ")
        choice = scanner.nextInt()

        when (choice) {
            0 -> return
            1 -> addMatrices()
            2 -> scalarMultiply()
            3 -> multiplyMatrix()
            4 -> { transposeMenu(); break }
            5 -> calculateDeterminant()
            6 -> inverseMatrix()
            else -> println("Invalid choice: $choice")
        }
    }
}

fun transposeMenu() {
    var choice = -1

    while (choice != 0) {
        println("0. Main Menu")
        println("1. Main diagonal")
        println("2. Side diagonal")
        println("3. Vertical line")
        println("4. Horizontal line")
        print("Your choice: ")
        choice = scanner.nextInt()

        when (choice) {
            0 -> break
            1 -> transposeMainMatrix()
            2 -> transposeSideMatrix()
            3 -> transposeVerticalLine()
            4 -> transposeHorizontalLine()
            else -> println("Invalid choice: $choice")
        }
    }
}

fun inverseMatrix() {
    var matrix = getMatrixParameters("Enter matrix size: ", "Enter Matrix: ")
    val det = matrix.determinant(matrix)
    if (det == 0.0) {
        println("This matrix doesn't have an inverse.")
        println()
        return
    }
    val matrix2 = matrix.calculateInverse(det)
    matrix2.printMatrix()
}

fun calculateDeterminant() {
    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter Matrix: ")
    println("The result is: ")
    println(matrix.determinant(matrix))
    println()
}

fun transposeMainMatrix() {
    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter Matrix: ")
    val matrix2 = matrix.transposeMain()
    println("The result is:")
    matrix2.printMatrix()
    println()
}

fun transposeSideMatrix() {
    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter Matrix: ")
    val matrix2 = matrix.transposeSide()
    println("The result is:")
    matrix2.printMatrix()
    println()
}

fun transposeVerticalLine() {
    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter Matrix: ")
    val matrix2 = matrix.transposeVertical()
    println("The result is:")
    matrix2.printMatrix()
    println()
}

fun transposeHorizontalLine() {
    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter Matrix: ")
    val matrix2 = matrix.transposeHorizontal()
    println("The result is:")
    matrix2.printMatrix()
    println()
}

fun getMatrixParameters(promptRC: String, promptEnter: String) : Matrix {
    var rows: Int
    var cols: Int

    while (true) {
        print(promptRC)
        rows = scanner.nextInt()
        cols = scanner.nextInt()
        if(rows > 0 && cols > 0) break
    }

    val matrix = Matrix(rows, cols)
    println(promptEnter)
    matrix.input()
    return matrix
}

fun scalarMultiply() {
    val k: Double

    val matrix = getMatrixParameters("Enter the size of the matrix: ", "Enter matrix: ")

    print("Enter constant: ")
    k = scanner.next().toDouble()

    val matrix2 = matrix * k
    println("The result is:")
    matrix2.printMatrix()
}

fun addMatrices() {
    var rows: Int
    var cols: Int

    val matrix1 = getMatrixParameters("Enter the size of the first matrix: ",
        "Enter first matrix: ")

    val matrix2 = getMatrixParameters("Enter the size of the second matrix: ",
        "Enter second matrix: ")

    if(matrix1.rows != matrix2.rows || matrix1.columns != matrix2.columns) {
        println("The operation cannot be performed.")
        return
    }
    
    val matrix3 = matrix1 + matrix2
    println("The result is:")
    matrix3.printMatrix()
}

fun multiplyMatrix() {
    var rows: Int
    var cols: Int

    val matrix1 = getMatrixParameters("Enter the size of the first matrix: ",
        "Enter first matrix: ")

    val matrix2 = getMatrixParameters("Enter the size of the second matrix: ",
        "Enter second matrix: ")

    if(matrix1.columns != matrix2.rows) {
        println("The operation cannot be performed.")
        return
    }

    val matrix3 = matrix1 * matrix2
    println("The result is:")
    matrix3.printMatrix()
}

class MatrixElement(_element: Double) {
    var dDouble = _element

    override fun toString(): String {
        return dDouble.toString()
    }

}

class Matrix(_rows: Int = 1, _columns: Int = 1) {
    var rows:Int = _rows
    var columns:Int = _columns
    var mdata: Array<Array<MatrixElement>> = Array(rows) {
        Array(columns) {
            MatrixElement(0.0)
        }
    }

    fun input() {
        var line: Array<Double>

        for (i in 0 until(rows)) {
            line = readLine()!!.trim().split(" ").map (String::toDouble).toTypedArray()
            for (j in 0 until(columns)) {
                mdata[i][j] = MatrixElement(line[j])
            }
        }
    }

    operator fun times(constant: Double): Matrix {
        val matrix2 = Matrix(rows, columns)

        for (i in 0 until(rows)) {
            for (j in 0 until(columns)) {
                val num1 = mdata[i][j].dDouble
                matrix2.mdata[i][j].dDouble = num1 * constant
            }
        }
        return matrix2
    }

    operator fun plus(matrix2: Matrix): Matrix {
        val matrix3 = Matrix(this.rows, this.columns)

        for (i in 0 until(this.rows)) {
            for (j in 0 until(this.columns)) {
                val num1 = this.mdata[i][j].dDouble
                val num2 = matrix2.mdata[i][j].dDouble
                matrix3.mdata[i][j].dDouble = num1 + num2
            }
        }
        return matrix3
    }

    operator fun times(matrix2: Matrix): Matrix {
        val matrix3 = Matrix( this.rows, matrix2.columns)
        var total = 0.0

        for (r in 0 until(this.rows)) {
            for (c in 0 until(matrix3.columns)) {
                total = 0.0
                for (k in 0 until(this.columns)) {
                    val num1 = this.mdata[r][k].dDouble
                    val num2 = matrix2.mdata[k][c].dDouble
                    total += num1 * num2
                }
                matrix3.mdata[r][c].dDouble = total
            }
        }
        return matrix3
    }

    fun copyMatrix() : Matrix {
        val matrix2 = Matrix(this.rows, this.columns)

        for (r in 0 until(this.rows)) {
            for (c in 0 until(this.columns)) {
                matrix2.mdata[r][c].dDouble = this.mdata[r][c].dDouble
            }
        }
        return matrix2
    }

    fun calcSubMatrix(i: Int, j: Int): Matrix {
        val matrix2 = Matrix(this.rows-1, this.columns-1)
        var px = 0
        var py = 0

        for (r in 0 until(this.rows)) {
            if (r == i) continue
            px = 0
            for (c in 0 until(this.columns)) {
                if (c == j) continue
                matrix2.mdata[py][px].dDouble = this.mdata[r][c].dDouble
                px++
            }
            py++
        }

        return matrix2
    }

    fun calculateCofactors(): Matrix {
        val matrix2 = copyMatrix()
        var sign = 0.0
        var subMatrix = Matrix(this.rows-1, this.columns-1)

        for (r in 0 until(this.rows)) {
            for (c in 0 until(this.columns)) {
                sign = (-1.0).pow(r+c)
                subMatrix = this.calcSubMatrix(r, c)
                matrix2.mdata[r][c].dDouble = sign * determinant(subMatrix)
            }
        }
        return matrix2.transposeMain()
    }

    fun calculateInverse(detA: Double): Matrix {
        if (this.rows == 2 && this.columns == 2) {
            return calc2by2Inverse(detA)
        }

        val matrix2 = calculateCofactors()
        val matrix3 = matrix2 * (1/detA)
        return matrix3
    }

    fun calc2by2Inverse(detA: Double): Matrix {
        var matrix2 = this.copyMatrix()
        val invDet = 1/detA
        matrix2.swap(0,0, 1,1)
        matrix2.mdata[0][1].dDouble = matrix2.mdata[0][1].dDouble * -1
        matrix2.mdata[1][0].dDouble = matrix2.mdata[1][0].dDouble * -1
        matrix2 = matrix2 * invDet
        return matrix2
    }

    fun transposeSide(): Matrix {
        var j = 0
        var y = 0
        val matrix2 = copyMatrix()

        for (i in 0 until(rows-1)) {
            j = rows - i - 1
            y = rows
            for (x in 0 until(j)) {
                y--
                matrix2.swap(i, x, y, j)
            }
        }
        return matrix2
    }

    fun transposeVertical(): Matrix {
        val matrix2 = copyMatrix()

        for (r in 0 until(this.rows)) {
            for (c in 0 until(this.columns / 2)) {
                matrix2.swap(r,c, r, this.columns-c-1)
            }
        }
        return matrix2
    }

    fun transposeHorizontal(): Matrix {
        val matrix2 = copyMatrix()

        for (c in 0 until(this.columns)) {
            for (r in 0 until(this.rows / 2)) {
                matrix2.swap(r,c, this.rows-r-1, c)
            }
        }
        return matrix2
    }

    fun transposeMain(): Matrix {
        val matrix2 = copyMatrix()
        for (i in 0 until(this.rows)) {
            for (j in i+1 until(this.columns)) {
                if (i != j) {
                    matrix2.swap(i, j, j, i)
                }
            }
        }
        return matrix2
    }

    fun determinant(matrix: Matrix): Double {
        var a = 0.0
        var b = 0.0
        var c = 0.0
        var d = 0.0
        var i = 0
        var sum = 0.0
        var cofactor = 0.0
        var px = 0
        var py = 0

        if (matrix.rows != matrix.columns) return 0.0
        if (matrix.rows == 2) {
            a = matrix.mdata[0][0].dDouble
            b = matrix.mdata[0][1].dDouble
            c = matrix.mdata[1][0].dDouble
            d = matrix.mdata[1][1].dDouble
            return a * d - b * c
        }

        val newMatrix = Matrix(matrix.rows-1, matrix.columns-1)

        for (j in 0 until(matrix.columns)) {
            cofactor = Math.pow(-1.0, (i+j).toDouble())  * matrix.mdata[i][j].dDouble
            py = 0
            for (y in i+1 until(matrix.rows)) {
                px = 0
                for (x in 0 until(j)) {
                    newMatrix.mdata[py][px] = matrix.mdata[y][x]
                    px++
                }
                for (x in j+1 until(matrix.columns)) {
                    newMatrix.mdata[py][px] = matrix.mdata[y][x]
                    px++
                }
                py++
            }
            sum += cofactor * determinant(newMatrix)
        }
        return sum
    }

    fun swap(r1: Int, c1: Int, r2:Int, c2:Int) {
        val x = this.mdata[r1][c1].dDouble
        this.mdata[r1][c1].dDouble = this.mdata[r2][c2].dDouble
        this.mdata[r2][c2].dDouble = x
    }

    fun printMatrix() {
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                print("${mdata[r][c]} ")
            }
            println()
        }
    }
}
