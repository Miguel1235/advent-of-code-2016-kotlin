private fun part1(input: List<String>): Int {
    val regex = Regex("""\w+""")

    fun supportsTls(toCheck: List<String>): Boolean {
        for(line in toCheck) {
            val f = line.chunked(2)
            val s = line.drop(1).chunked(2)
            val r1 = f.zipWithNext().any { (f,s) -> (f == s.reversed() || f.reversed() == s) && f != s }
            val r2 = s.zipWithNext().any { (f,s) -> (f == s.reversed() || f.reversed() == s) && f != s }

            if(r1 || r2) return true
        }
        return false
    }

    var total = 0

    for(line in input) {
        val r = regex.findAll(line).toList().map { it.value }

        val brackets = r.filterIndexed { index, _ -> index % 2 == 1 }
        val notBracket = r.filterIndexed { index, _ -> index % 2 == 0 }

        if(supportsTls(notBracket) && !supportsTls(brackets)) {
            total++
        }
    }

    return total
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 2)
//    check(part2(testInput) == 0)
     
    val input = readInput("Day07")
    check(part1(input) == 118)
//    check(part2(input) == 0)
}
 