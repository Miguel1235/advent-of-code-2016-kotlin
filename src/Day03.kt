private val isValidTriangle = { originalList: List<Int>, combinations: List<List<Int>> ->
    combinations.fold(0) { acc, combination ->
        val notInCombination = findValuesNotInCombination(originalList, combination)
        if (combination.sum() > notInCombination) {
            acc + 1
        } else {
            acc
        }
    } == 3
}

private fun findValuesNotInCombination(originalList: List<Int>, combination: List<Int>): Int {
    val originalFrequency = originalList.groupingBy { it }.eachCount()
    val combinationFrequency = combination.groupingBy { it }.eachCount()
    val result = mutableListOf<Int>()

    for ((value, count) in originalFrequency) {
        val combinationCount = combinationFrequency[value] ?: 0
        repeat(count - combinationCount) {
            result.add(value)
        }
    }
    return result.first()
}

private val parseInput = { input: List<String> ->
    val triangleRegex = Regex("""(\d+)\s+(\d+)\s+(\d+)""")
    input.map { it ->
        triangleRegex.find(it)!!.groupValues.drop(1).map { it.toInt() }
    }
}

private val part1 = { input: List<List<Int>> ->
    input.sumOf {
        val combinations = combinations(it, 2)
        if (isValidTriangle(it, combinations)) 1 else 0.toInt()
    }
}

private val part2 = { input: List<List<Int>> ->
    val newInput = (input.map { it[0] } + input.map { it[1] } + input.map { it[2] }).chunked(3)
    part1(newInput)
}

fun main() {
    val testInput = parseInput(readInput("Day03_test"))
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = parseInput(readInput("Day03"))
    check(part1(input) == 869)
    check(part2(input) == 1544)
}
 