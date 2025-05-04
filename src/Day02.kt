private enum class DeltaDirection(val rowDelta: Int, val colDelta: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}

private fun move(keyPad: List<List<Any?>>, position: Pair<Int, Int>, direction: DeltaDirection): Pair<Int, Int> {
    val (r, c) = position
    val newRow = r + direction.rowDelta
    val newCol = c + direction.colDelta

    return if (keyPad.getOrNull(newRow)?.getOrNull(newCol) != null) {
        position.copy(newRow, newCol)
    } else {
        position
    }
}

private fun part1(input: List<String>, keyPad: List<List<Any?>>): String {
    var currentPosition = keyPad.indexOfFirst { row -> row.contains(5) }
        .let { rowIndex ->
            if (rowIndex >= 0) {
                Pair(rowIndex, keyPad[rowIndex].indexOf(5))
            } else {
                null
            }
        }!!

    return buildString {
        for(instruction in input) {
            for(move in instruction) {
                currentPosition = when(move) {
                    'U' -> move(keyPad, currentPosition, DeltaDirection.UP)
                    'D' -> move(keyPad, currentPosition, DeltaDirection.DOWN)
                    'L' -> move(keyPad, currentPosition, DeltaDirection.LEFT)
                    'R' -> move(keyPad, currentPosition, DeltaDirection.RIGHT)
                    else -> currentPosition
                }
            }
            append(keyPad[currentPosition.first][currentPosition.second])
        }
    }
}

fun main() {
    val keyPad = (1..9).chunked(3)
    val customKeyPad: List<List<Any?>> = listOf(
        listOf(null, null, 1, null, null),
        listOf(null, 2, 3, 4, null),
        listOf(5, 6, 7, 8, 9),
        listOf(null,'A','B','C', null),
        listOf(null, null, 'D', null, null)
    )


    val testInput = readInput("Day02_test")
    check(part1(testInput, keyPad) == "1985")
    check(part1(testInput, customKeyPad) == "5DB3")

    val input = readInput("Day02")
    check(part1(input, keyPad) == "98575")
    check(part1(input, customKeyPad) == "CD8D4")
}
 