private fun part1(discs: List<List<Int>>): Int {
    var i = 0
    while(true) {
        val results = discs.map { (disk, positions, _, position) -> (i + position + (disk -1)) % positions }
        if(results.all { it == 0}) return i-1
        i++
    }
}

private fun parseInput(input: List<String>): List<List<Int>> {
    val numberRegex = Regex("""\d+""")

    return input.map {
        numberRegex.findAll(it).map { it.value.toInt() }.toList()
    }
}

fun main() {
    val testInput = parseInput(readInput("Day15_test"))
    check(part1(testInput) == 5)

    val input = parseInput(readInput("Day15"))
    check(part1(input) == 16824)

    val newInput = input + listOf(listOf(7, 11, 0, 0))
    check(part1(newInput) == 3543984)
}
 