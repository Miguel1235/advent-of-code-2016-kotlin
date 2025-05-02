import kotlin.math.abs

private fun part1(input: List<String>): Int {
    val instructions = parseInput(input)
    var row = 0
    var col = 0

    var dir = Direction.UP
//    val points: MutableList<Pair<Int, Int>> = mutableListOf()

    for(instruction in instructions) {
        when {
            dir == Direction.UP && instruction.dir == Direction.RIGHT -> {
                dir = Direction.RIGHT
                row += instruction.amount
            }
            dir == Direction.UP && instruction.dir == Direction.LEFT -> {
                dir = Direction.LEFT
                row -= instruction.amount
            }
            dir == Direction.DOWN && instruction.dir == Direction.RIGHT -> {
                dir = Direction.LEFT
                row -= instruction.amount
            }
            dir == Direction.DOWN && instruction.dir == Direction.LEFT -> {
                dir = Direction.RIGHT
                row += instruction.amount
            }
            dir == Direction.RIGHT && instruction.dir == Direction.LEFT -> {
                dir = Direction.UP
                col += instruction.amount
            }
            dir == Direction.RIGHT && instruction.dir == Direction.RIGHT -> {
                dir = Direction.DOWN
                col -= instruction.amount
            }
            dir == Direction.LEFT && instruction.dir == Direction.LEFT -> {
                dir = Direction.DOWN
                col -= instruction.amount
            }
            dir == Direction.LEFT && instruction.dir == Direction.RIGHT -> {
                dir = Direction.UP
                col += instruction.amount
            }
        }
//        points.add(Pair(row, col))

    }
//    println(points)
    return abs(row)+abs(col)
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
        for(line in lines) {
            val (_, dir, amount) = regex.find(line)!!.groupValues
            add(Instruction(if(dir == "L") Direction.LEFT else Direction.RIGHT, amount.toInt()))
        }
    }
    return instructions
}

fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 8)
//    check(part2(testInput) == 0)
     
    val input = readInput("Day01")
    check(part1(input) == 230)
//    check(part2(input) == 0)
}
 