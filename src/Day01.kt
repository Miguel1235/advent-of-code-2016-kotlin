import kotlin.math.abs

private fun part1(input: List<String>, isPart2: Boolean = false): Int {
    val instructions = parseInput(input)
    var dir = Direction.UP
    val path = mutableListOf(Pair(0, 0))

    val directionChanges =
        mapOf(
            Direction.UP to mapOf(
                Direction.RIGHT to Pair(Direction.RIGHT) { p: Pair<Int, Int> -> p.copy(first = p.first + 1) },
                Direction.LEFT to Pair(Direction.LEFT) { p: Pair<Int, Int> -> p.copy(first = p.first - 1) }),
            Direction.DOWN to mapOf(
                Direction.RIGHT to Pair(Direction.LEFT) { p: Pair<Int, Int> -> p.copy(first = p.first - 1) },
                Direction.LEFT to Pair(Direction.RIGHT) { p: Pair<Int, Int> -> p.copy(first = p.first + 1) }),
            Direction.RIGHT to mapOf(
                Direction.LEFT to Pair(Direction.UP) { p: Pair<Int, Int> -> p.copy(second = p.second + 1) },
                Direction.RIGHT to Pair(Direction.DOWN) { p: Pair<Int, Int> -> p.copy(second = p.second - 1) }),
            Direction.LEFT to mapOf(
                Direction.LEFT to Pair(Direction.DOWN) { p: Pair<Int, Int> -> p.copy(second = p.second - 1) },
                Direction.RIGHT to Pair(Direction.UP) { p: Pair<Int, Int> -> p.copy(second = p.second + 1) })
        )

    instructions.forEach {
        val (newDir, moveFunc) = directionChanges[dir]!![it.dir]!!
        dir = newDir
        repeat(it.amount) {
            path.add(moveFunc(path.last()))
        }
    }

    val finalPos = if (isPart2) {
        path.groupingBy { it }.eachCount().filter { it.value >= 2 }.entries.first().key
    } else {
        path.last()
    }

    return abs(finalPos.first) + abs(finalPos.second)
}

private enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

private data class Instruction(val dir: Direction, val amount: Int)

private fun parseInput(input: List<String>): List<Instruction> {
    val regex = Regex("""([LR])(\d+)""")
    val lines = input[0].split(",").map { it.trim() }
    val instructions = buildList {
        for (line in lines) {
            val (_, dir, amount) = regex.find(line)!!.groupValues
            add(Instruction(if (dir == "L") Direction.LEFT else Direction.RIGHT, amount.toInt()))
        }
    }
    return instructions
}

fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 8)
    check(part1(testInput, true) == 4)

    val input = readInput("Day01")
    check(part1(input) == 230)
    check(part1(input, true) == 154)
}