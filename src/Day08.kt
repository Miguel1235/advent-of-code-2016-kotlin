private fun swiftRow(elements: MutableList<String>, positions: Int): MutableList<String> {
    if (elements.isEmpty() || positions % elements.size == 0) return elements

    val effectivePositions = positions % elements.size
    repeat(effectivePositions) {
        val last = elements.removeLast()
        elements.add(0, last)
    }
    return elements
}

private fun part1(instructions: List<Ins>): Int {
    var grid = MutableList(6) { MutableList(50) { "." } }

    for (instruction in instructions) {
        when (instruction.action) {
            Action.RECT -> {
                for (i in 0 until instruction.height!!) {
                    for (j in 0 until instruction.width!!) {
                        grid[i][j] = "*"
                    }
                }
            }
            Action.ROTATE_ROW -> {
                val row = grid[instruction.index!!]
                val steps = instruction.steps!!
                grid[instruction.index] = swiftRow(row, steps)
            }
            Action.ROTATE_COLUMN -> {
                val steps = instruction.steps!!

                val transposed = transpose(grid)
                val row = transposed[instruction.index!!]

                transposed[instruction.index] = swiftRow(row, steps)

                grid = transpose(transposed)
            }
        }

    }
    prettyPrint(grid)// to see the words for the second part!! -> result: EOARGPHYAO
    return grid.flatten().count { it == "*" }
}

enum class Action {
    RECT, ROTATE_ROW, ROTATE_COLUMN;
}


data class Ins(
    val action: Action,
    val width: Int? = null,
    val height: Int? = null,
    val index: Int? = null,
    val steps: Int? = null
)

private fun parseInput(input: List<String>): List<Ins> {
    val rectPattern = Regex("rect (\\d+)x(\\d+)")
    val rotatePattern = Regex("rotate (column|row) ([xy])=(\\d+) by (\\d+)")
    return buildList {
        for (line in input) {
            val rectMatch = rectPattern.matchEntire(line)
            if (rectMatch != null) {
                val (width, height) = rectMatch.destructured
                add(Ins(Action.RECT, width.toInt(), height.toInt()))
                continue
            }
            val rotateMatch = rotatePattern.matchEntire(line)
            val (_, axis, index, steps) = rotateMatch!!.destructured
            add(
                Ins(
                    if (axis == "y") Action.ROTATE_ROW else Action.ROTATE_COLUMN,
                    index = index.toInt(),
                    steps = steps.toInt()
                )
            )
        }
    }
}

fun main() {
    val testInput = parseInput(readInput("Day08_test"))
    check(part1(testInput) == 6)

    val input = parseInput(readInput("Day08"))
    check(part1(input) == 128)
}
 