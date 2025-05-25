private fun part(input: List<List<Char>>, rows: Int = 10): Int {
    val width = input.first().size
    val growInput = input.toMutableList()
    var row = 1
    repeat(rows -1 ) {
        val newRow = buildList {
            for(w in 0 until width) {
                val titles = obtainTitles(growInput, row, w)

                val tileType = obtainTileType(titles)
                add(tileType)
            }
        }
        row++
        growInput.add(newRow)
    }

    return growInput.flatten().count { it == '.' }
}

private fun obtainTileType(titles: List<Char>): Char {
    val (l,c,r) = titles

    return when {
        l == '^' && c == '^' && r == '.' -> '^'
        l == '.' && c == '^' && r == '^' -> '^'
        l == '^' && c == '.' && r == '.' -> '^'
        l == '.' && c == '.' && r == '^' -> '^'
        else -> '.'
    }
}

private fun obtainTitles(input: List<List<Char>>, row: Int, col: Int): List<Char> {
    return buildList {
        for(dir in UpperDirections.entries) {
            val newRow = row + dir.rDelta
            val newCol = col + dir.cDelta
            add(input.getOrNull(newRow)?.getOrNull(newCol) ?: '.')
        }
    }
}

private fun parseInput(input: List<String>): List<List<Char>> {
    return listOf(
        input.first().toList()
    )
}

fun main() {
    val testInput = parseInput(readInput("Day18_test"))
    check(part(testInput) == 38)
     
    val input = parseInput(readInput("Day18"))
    check(part(input, 40) == 1989)
    check(part(input, 400_000) == 19999894)
}
 