private fun part(input: List<List<Char>>, isSecondPart: Boolean = false): String {
    val comparator: Comparator<Pair<Char,Int>> = if(isSecondPart) compareBy { it.second } else compareByDescending { it.second }
    return buildString {
        input.forEach {
            append(it
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

private fun parseInput(input: List<String>): List<List<Char>> {
    val inputList = input.map { it.toList() }
    return buildList {
        for(i in 0..<inputList[0].size) {
            val row = buildList {
                for(j in 0..<inputList.size) {
                    add(inputList[j][i])
                }
            }
            add(row)
        }
    }
}

fun main() {
    val testInput = parseInput(readInput("Day06_test"))
    check(part(testInput) == "easter")
    check(part(testInput, true) == "advent")
     
    val input = parseInput(readInput("Day06"))
    check(part(input) == "tsreykjj")
    check(part(input, true) == "hnfbujie")
}
 