private fun part(data: String, diskLength: Int = 20): String {
    var dragonCurve = obtainDragonCurve(data)
    while(dragonCurve.length < diskLength) {
        dragonCurve = obtainDragonCurve(dragonCurve)
    }
    dragonCurve = dragonCurve.substring(0, diskLength)
    val checksum = obtainChecksum(dragonCurve)
    return checksum
}

private fun obtainChecksum(dragonCurve: String): String {
    val pairs = dragonCurve.windowed(2,2, false)
    var resultString = pairs.map { if(it[0] == it[1]) '1' else '0' }.joinToString("")
    while(resultString.length % 2 == 0) {
        val pairs = resultString.windowed(2,2, false)
        resultString = pairs.map { if(it[0] == it[1]) '1' else '0' }.joinToString("")
    }
    return resultString
}

private fun obtainDragonCurve(data: String): String {
    val a = data
    val b = a.reversed().map { if(it == '0') '1' else '0' }.joinToString("")
    return "${a}0${b}"
}

fun main() {
    val testInput = readInput("Day16_test").first()
    check(part(testInput) == "01100")

    val input = readInput("Day16").first()
    check(part(input, 272) == "11100110111101110")
    check(part(input, 35651584) == "10001101010000101")
}
 