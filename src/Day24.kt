private fun part1(input: List<String>): Int {
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

enum class Layout {
    WALL,
    SPACE,
    RELEVANT_LOCATION
}

private fun parseInput(input: List<String>): List<List<Layout>> {
    return buildList {
        for(row in input) {
            add(
                row.toList().map {
                    when(it) {
                        '#' -> Layout.WALL
                        '.' -> Layout.SPACE
                        else -> Layout.RELEVANT_LOCATION
                    }
                }
            )
        }
    }
}

fun main() {
    val testInput = readInput("Day24_test")
    parseInput(testInput)
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
//    val input = readInput("Day24")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 