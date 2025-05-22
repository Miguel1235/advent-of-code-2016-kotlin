private val part = { data: String, diskLength: Int ->
    var dragonCurve = obtainDragonCurve(data)
    while (dragonCurve.length < diskLength) {
        dragonCurve = obtainDragonCurve(dragonCurve)
    }
    obtainChecksum(dragonCurve.take(diskLength))
}

private val obtainChecksum = { dragonCurve: String ->
    var result = dragonCurve
    do {
        result = result.chunked(2).map { if (it[0] == it[1]) '1' else '0' }.joinToString("")
    } while (result.length % 2 == 0)
    result
}

private val obtainDragonCurve = { data: String ->
    buildString {
        append(data)
        append('0')
        append(data.reversed().map { if (it == '0') '1' else '0' }.joinToString(""))
    }
}

fun main() {
    val testInput = readInput("Day16_test").first()
    check(part(testInput, 20) == "01100")

    val input = readInput("Day16").first()
    check(part(input, 272) == "11100110111101110")
    check(part(input, 35651584) == "10001101010000101")
}
 