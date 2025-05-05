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