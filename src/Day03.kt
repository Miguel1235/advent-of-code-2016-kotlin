private fun isValidTriangle(originalList: List<Int>, combinations: List<List<Int>>): Boolean {
    return combinations.fold(0) { acc, combination ->
        val notInCombination = findValuesNotInCombination(originalList, combination).first()
        if (combination.sum() > notInCombination) {
            acc + 1
        } else {
            acc
        }
    } == 3
}

private fun part1(input: List<String>): Int {
    val triangleRegex = Regex("""(\d+)\s+(\d+)\s+(\d+)""")
    return input.sumOf {
        val result = triangleRegex.find(it)!!.groupValues.drop(1).map { it.toInt() }
        val combinations = combinations(result, 2)
        if(isValidTriangle(result, combinations)) 1 else 0.toInt()
    }
}


fun findValuesNotInCombination(originalList: List<Int>, combination: List<Int>): List<Int> {
    val originalFrequency = originalList.groupingBy { it }.eachCount()
    val combinationFrequency = combination.groupingBy { it }.eachCount()

    val result = mutableListOf<Int>()

    for ((value, count) in originalFrequency) {
        val combinationCount = combinationFrequency[value] ?: 0

        repeat(count - combinationCount) {
            result.add(value)
        }
    }

    return result
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)

    val input = readInput("Day03")
    check(part1(input) == 869)
//    check(part2(input) == 0)
}
 