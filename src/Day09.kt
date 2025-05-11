import kotlin.collections.component1
import kotlin.collections.component2

private fun replaceValues(line: String): String {
//    println("The line is $line")

    val bracesRegex = Regex("""\((\d+)x(\d+)\)""")
    val results = bracesRegex.findAll(line)

    if(results.toList().isEmpty()) return line

    for(result in results) {
        val (toTake, repeats) = result.groupValues.drop(1)
        val range = result.range
        val startTaken = range.last + 1

        val lettersTaken = line.substring(startTaken, startTaken + toTake.toInt())

        return replaceValues(line.replaceRange(range,lettersTaken.repeat(repeats.toInt()-1)))
    }
    return ""
}

private fun transformString(input: String): String {
    val regex = Regex("""\([^\)]+\)""")
    val matches = regex.findAll(input).toList()

    val result = StringBuilder(input)
    val replaced = BooleanArray(input.length)

    var i = 0
    while (i < matches.size) {
        val group = mutableListOf<MatchResult>()
        group.add(matches[i])

        var j = i + 1
        while (j < matches.size && matches[j].range.first == matches[j - 1].range.last + 1) {
            group.add(matches[j])
            j++
        }

        if (group.size > 1) {
            for (k in 1 until group.size) {
                val match = group[k]
                val start = match.range.first
                val end = match.range.last
                val content = match.value.drop(1).dropLast(1) // Remove parentheses

                val replacement = "#$content#"
                // Replace characters in-place
                for (p in start..end) {
                    result.setCharAt(p, ' ') // Placeholder for now
                    replaced[p] = true
                }
                // Replace from the start position
                for ((offset, ch) in replacement.withIndex()) {
                    if (start + offset <= end) {
                        result.setCharAt(start + offset, ch)
                    }
                }
            }
        }

        i = j
    }

    return result.toString()
}

private fun part1(input: List<String>): Int {
    for(line in input) {
        println(transformString(line))
//        val r = replaceValues(transformString(line))
//        println("The result is $r -- size: ${r.length}")
    }
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
//    val testInput = readInput("Day09_test")
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
    val input = readInput("Day09")
    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 