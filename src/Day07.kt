private fun supportsTls(toCheck: List<String>): Boolean {
    for (line in toCheck) {
        val f = line.chunked(2)
        val s = line.drop(1).chunked(2)
        val r1 = f.zipWithNext().any { (f, s) -> (f == s.reversed() || f.reversed() == s) && f != s }
        val r2 = s.zipWithNext().any { (f, s) -> (f == s.reversed() || f.reversed() == s) && f != s }

        if (r1 || r2) return true
    }
    return false
}

private fun parseInput(input: List<String>): List<List<String>> {
    val regex = Regex("""\w+""")

    return buildList {
        for (line in input) {
            add(regex.findAll(line).toList().map { it.value })
        }
    }
}

private fun part1(input: List<List<String>>): Int {
    var total = 0
    for (line in input) {
        val brackets = line.filterIndexed { index, _ -> index % 2 == 1 }
        val notBracket = line.filterIndexed { index, _ -> index % 2 == 0 }

        if (supportsTls(notBracket) && !supportsTls(brackets)) {
            total++
        }
    }
    return total
}

private fun part2(input: List<List<String>>): Int {
    var total = 0

    for (line in input) {
        val brackets = line.filterIndexed { index, _ -> index % 2 == 1 }
        val notBracket = line.filterIndexed { index, _ -> index % 2 == 0 }

        for (bracket in brackets) {

            val all = bracket.windowed(3)

            for (third in all) {
                val (s, m, e) = third.toList()
                if (s == e) {
                    val op = "$m$e$m"
                    if (notBracket.any { it.contains(op) }) {
                        total++
                        break
                    }
                }
            }
        }
    }
    return total
}

fun main() {
    val testInput = parseInput(readInput("Day07_test"))
    check(part1(testInput) == 0)
    check(part2(testInput) == 3)

    val input = parseInput(readInput("Day07"))
    check(part1(input) == 118)
    check(part2(input) == 260)
}
 