import kotlin.math.floor

private fun josephus(n: Int): Int {
    var winner = 0
    for (i in 2..n) {
        winner = (winner + 2) % i
    }
    return winner + 1
}

private fun part1(input: List<String>): Int {
    return josephus(input.first().toInt())
}

fun main() {
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 3)

    val input = readInput("Day19")
    check(part1(input) == 1815603)
}
 