private val part1 = { rooms: List<Room> ->
    rooms.sumOf { (letters, sectorId, checksum) ->
        val joinedRooms = letters.split("-").joinToString("") { it.trim() }
        val sortedPairs = joinedRooms.groupingBy { it }.eachCount().toList()
            .sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
        val sortedList = sortedPairs.take(5).map { it.first }.joinToString("")

        if (sortedList == checksum) sectorId.toInt() else 0
    }
}

private val part2 = { rooms: List<Room> ->
    rooms.find { (letters, sectorId) ->
        val result = letters.map { offsetLetter(it, sectorId) }.joinToString("")
        result == "northpole object storage"
    }!!.sectorId
}

data class Room(val letters: String, val sectorId: Int, val checksum: String)

private fun parseInput(input: List<String>): List<Room> {
    val regex = Regex("""^((?:[a-zA-Z]+-)+[a-zA-Z]+)-(\d+)\[([a-zA-Z]+)]$""")
    return buildList {
        input.map {
            val (letters, sectorId, checksum) = regex.find(it)!!.groupValues.drop(1)
            add(Room(letters, sectorId.toInt(), checksum))
        }
    }
}

fun main() {
    val testInput = parseInput(readInput("Day04_test"))
    check(part1(testInput) == 1514)

    val input = parseInput(readInput("Day04"))
    check(part1(input) == 278221)
    check(part2(input) == 267)
}
 