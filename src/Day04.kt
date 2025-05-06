private fun part1(input: List<String>): Int {
    val regex = Regex("""^((?:[a-zA-Z]+-)+[a-zA-Z]+)-(\d+)\[([a-zA-Z]+)]$""")
    return input.sumOf { line ->
            val (letters, sectorId, checksum) = regex.find(line)!!.groupValues.drop(1)
            val joinedRooms = letters.split("-").joinToString("") { it.trim() }
            val sortedPairs = joinedRooms.groupingBy { it }.eachCount().toList().sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
            val sortedList = sortedPairs.take(5).map { it.first }.joinToString("")

            if(sortedList == checksum) sectorId.toInt() else 0
    }
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 1514)
    check(part2(testInput) == 0)
     
    val input = readInput("Day04")
    check(part1(input) == 278221)
//    check(part2(input) == 0)
}
 