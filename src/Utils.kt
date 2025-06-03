import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Get combinations of size k from a list
 */
fun <T> combinations(list: List<T>, k: Int): List<List<T>> {
    val results = mutableListOf<List<T>>()

    fun backtrack(start: Int, current: MutableList<T>) {
        if (current.size == k) {
            results.add(current.toList())
            return
        }
        for (i in start..list.lastIndex) {
            current.add(list[i])
            backtrack(i + 1, current)
            current.removeLast()
        }
    }
    backtrack(0, mutableListOf())
    return results
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (size <= 1) return listOf(this)

    val result = mutableListOf<List<T>>()
    for (i in indices) {
        val element = this[i]
        val remaining = this.toMutableList().apply { removeAt(i) }
        val perms = remaining.permutations()
        result.addAll(perms.map { listOf(element) + it })
    }
    return result
}

fun offsetLetter(letter: Char, offset: Int): Char {
    val lower = letter.lowercaseChar()
    if(lower == '-') return ' '

    val baseP = lower - 'a'
    val newP = (baseP + offset) % 26

    val wrappedPosition = if (newP < 0) newP + 26 else newP
    return 'a' + wrappedPosition
}

fun<T> prettyPrint(grid: List<List<T>>) {
    println()
    for(rowI in grid.indices) {
        val row = grid[rowI]
        for(colI in row.indices) {
            print(row[colI])
        }
        println()
    }
}

fun <T> transpose(matrix: List<List<T>>): MutableList<MutableList<T>> {
    if (matrix.isEmpty()) return mutableListOf()

    val rows = matrix.size
    val cols = matrix[0].size

    return MutableList(cols) { col ->
        MutableList(rows) { row ->
            matrix[row][col]
        }
    }
}

enum class DeltaDirection(val rowDelta: Int, val colDelta: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}

enum class UpperDirections(val rDelta: Int, val cDelta: Int) {
    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
}