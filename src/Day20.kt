private fun part(ranges: List<Pair<Long, Long>>, isPart1: Boolean = true, maxRange: Long = 4294967295): Long {
    var allowedIps = 0
    for(counter in 0..maxRange) {
        var allowedIp = true
        for(range in ranges) {
            if(isInRange(counter.toLong(), range)) {
                allowedIp = false
                break
            }
        }
        if(allowedIp) {
            if(isPart1)  return counter
            allowedIps++
        }
    }
    return allowedIps.toLong()
}

val parseInput = { input: List<String> -> input.map {
        val (start, end) = it.split("-")
        start.toLong() to end.toLong()
    }
}

val isInRange = { value: Long, range: Pair<Long, Long> ->
    value >= range.first && value <= range.second
}


fun main() {
    val testInput = parseInput(readInput("Day20_test"))
    check(part(testInput) == 3L)
    check(part(testInput, false, 9) == 2L)

    val input = parseInput(readInput("Day20"))
    check(part(input) == 32259706L)
    check(part(input, false) == 113L)
}
 