private val part = { input: List<List<Char>>, useLeastCommon: Boolean ->
    val comparator: Comparator<Pair<Char, Int>> = if (useLeastCommon) compareBy { it.second } else compareByDescending { it.second }
    buildString {
        input.forEach {
            append(
                it
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedWith(comparator)
                .first()
                .first
            )
        }
    }
}

private val parseInput6 = { input: List<String> ->
    (0 until input[0].length).map { columnIndex ->
        input.mapNotNull { row ->
            row.getOrNull(columnIndex)
        }
    }
}

fun main() {
    val testInput = parseInput6(readInput("Day06_test"))
    check(part(testInput, false) == "easter")
    check(part(testInput, true) == "advent")

    val input = parseInput6(readInput("Day06"))
    check(part(input, false) == "tsreykjj")
    check(part(input, true) == "hnfbujie")
}
 