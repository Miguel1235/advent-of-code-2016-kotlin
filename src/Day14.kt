private fun part(salt: String, isPart1: Boolean = true): Int {
    var startIndex = 0
    var keyNumber = 0
    while (true) {
        val firstT = findTriplet(salt, startIndex, Int.MAX_VALUE, "", isPart1)
        val start = firstT.first + 1
        val end = start + 1000
        val fiveOfAKind = firstT.second[0].toString().repeat(5)
        val secondT = findTriplet(salt, start, end, fiveOfAKind, isPart1)
        if (secondT.first != -1) {
            keyNumber += 1
            println("current key: $keyNumber")
        }
        if (keyNumber == 64) {
            return start - 1
        }
        startIndex = start
    }
}


private fun findStretchedHash(hash: String): String {
    var current = hash
    repeat(2017) {
        current = current.md5()
    }
    return current
}

private fun findTriplet(
    salt: String,
    start: Int = 0,
    end: Int,
    match: String = "",
    isPart1: Boolean
): Pair<Int, String> {
    var currentIndex = start

    while (currentIndex < end) {
        val md5Hash = if (isPart1) "$salt${currentIndex}".md5() else findStretchedHash("${salt}${currentIndex}")
        val triple = md5Hash.windowed(if (match.isEmpty()) 3 else 5).firstOrNull { it.toSet().size == 1 }

        if (triple != null) {
            if (match.isEmpty()) return Pair(currentIndex, triple)
            if (triple == match) return Pair(currentIndex, triple)
        }
        currentIndex++
    }
    return Pair(-1, "")
}

fun main() {
    val testInput = readInput("Day14_test").first()
    check(part(testInput) == 22728)
    check(part(testInput, false) == 22859)

    val input = readInput("Day14").first()
    check(part(input) == 35186)
    part(input, false).println()
    check(part(input, false) == 22429)
}
 