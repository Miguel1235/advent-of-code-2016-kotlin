private fun part1(doorId: String, repeats: Int = 8): String {
    var currentIdx = 0L

    return buildString {
        repeat(repeats) {
            while(!"$doorId$currentIdx".md5().startsWith("00000")) {
                currentIdx++
            }
            append("$doorId$currentIdx".md5()[5])
            currentIdx++
        }
    }
}

private fun part2(doorId: String): String {
    var currentIdx = 0L
    val password : MutableList<Char?> = buildList { repeat(8) { add(null) } }.toMutableList()
    while(password.any { it == null }) {
        while(!"$doorId$currentIdx".md5().startsWith("00000")) {
            currentIdx++
        }
        val md5Hash = "$doorId$currentIdx".md5()
        val position = md5Hash[5]
        val number = md5Hash[6]
        currentIdx++

        if(!position.isDigit() || position.digitToInt() > 7 ) continue
        if(password[position.digitToInt()] == null) password[position.digitToInt()] = number
    }
    return password.joinToString("")
}

fun main() {
    val testInput = readInput("Day05_test").first()
    check(part1(testInput, 3) == "18f")
    check(part2(testInput) == "05ace8e3")

    val input = readInput("Day05").first()
    check(part1(input, 8) == "4543c154")
    check(part2(input) == "1050cbbd")
}
 