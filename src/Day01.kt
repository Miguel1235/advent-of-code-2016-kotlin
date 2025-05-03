import kotlin.math.abs

private fun part1(input: List<String>, isPart2: Boolean = false): Int {
    val instructions = parseInput(input)

    var dir = Direction.UP
    val path: MutableList<Pair<Int, Int>> = mutableListOf(Pair(0, 0))

    for (instruction in instructions) {
        when {
            dir == Direction.UP && instruction.dir == Direction.RIGHT -> {
                dir = Direction.RIGHT
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(++row, col))
                }
            }

            dir == Direction.UP && instruction.dir == Direction.LEFT -> {
                dir = Direction.LEFT
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(--row, col))
                }
            }

            dir == Direction.DOWN && instruction.dir == Direction.RIGHT -> {
                dir = Direction.LEFT

                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(--row, col))
                }
            }

            dir == Direction.DOWN && instruction.dir == Direction.LEFT -> {
                dir = Direction.RIGHT
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(++row, col))
                }
            }

            dir == Direction.RIGHT && instruction.dir == Direction.LEFT -> {
                dir = Direction.UP
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(row, ++col))
                }
            }

            dir == Direction.RIGHT && instruction.dir == Direction.RIGHT -> {
                dir = Direction.DOWN
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(row, --col))
                }
            }

            dir == Direction.LEFT && instruction.dir == Direction.LEFT -> {
                dir = Direction.DOWN
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(row, --col))
                }
            }

            dir == Direction.LEFT && instruction.dir == Direction.RIGHT -> {
                dir = Direction.UP
                var (row, col) = path.last()
                repeat(instruction.amount) {
                    path.add(Pair(row, ++col))
                }
            }
        }

    }

    val (row, col) = if (isPart2) {
        path.groupingBy { it }.eachCount().filter { it.value >= 2 }.entries.first().key
    } else {
        path.last()
    }
    return abs(row) + abs(col)
}

private fun part2(input: List<String>): Int {
    return 0
}

enum class Direction {
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
 