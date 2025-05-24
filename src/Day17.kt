import kotlin.collections.indices

private enum class Entity {
    WALL,
    DOOR,
    VAULT,
    SPACE,
    START
}

private fun findEntity(grid: List<List<Entity>>, entity2Search: Entity): Pair<Int, Int> {
    for(rowI in grid.indices) {
        val row = grid[rowI]
        for(colI in row.indices) {
            val entity = row[colI]
            if(entity == entity2Search) {
                return Pair(rowI, colI)
            }
        }
    }
    return Pair(-1,-1)
}

private data class State(val row: Int, val col: Int, val input: String, val cost: Int)

private fun part1(input: String): String {
    val grid = parseInput()
    val start = findEntity(grid,Entity.START)
    val endPoint = findEntity(grid, Entity.VAULT)

    val queue = ArrayDeque<State>()
    val startState = State(start.first, start.second, input, 0)
    queue.add(startState)

    while(queue.isNotEmpty()) {
//        println("****")
//        println(queue)
        val (row, col, input, cost) = queue.removeFirst()
//        println("row: $row, col: $col, cost: $cost input: $input md5: ${input.md5().take(4)}")

        if(row == endPoint.first -1 && col == endPoint.second -1) {
            val result = input.toList().filter { it.isUpperCase() }.joinToString("")
            println("reaching end point!! $result")
            return result
        }

        for (direction in DeltaDirection.entries) {
            val newRow = row + direction.rowDelta
            val newCol = col + direction.colDelta

            val entity = grid.getOrNull(newRow)?.getOrNull(newCol)
            if(entity == null) continue
            if(entity != Entity.DOOR) continue


            val (up, down, left, right) = input.md5().take(4).toList().map { listOf('b', 'c','d', 'e', 'f').contains(it) }

            if(up && direction.name == "UP") {
//                println("We can move up because the door is open")
                queue.add(State(newRow-1, newCol, "${input}U", cost + 1))
//                visited[newRow][newCol] = true
//                continue
            }
            if(down && direction.name == "DOWN") {
//                println("We can move down because the door is open")
                queue.add(State(newRow+1, newCol, "${input}D", cost + 1))
//                visited[newRow][newCol] = true
//                continue
            }
            if(left && direction.name == "LEFT") {
//                println("We can move left because the door is open")
                queue.add(State(newRow, newCol-1, "${input}L", cost + 1))
//                visited[newRow][newCol] = true
//                continue
            }
            if(right && direction.name == "RIGHT") {
//                println("We can move right because the door is open")
                queue.add(State(newRow, newCol+1, "${input}R", cost + 1))
//                visited[newRow][newCol] = true
//                continue
            }
        }
    }
    return ""
}

private fun parseInput(): List<List<Entity>> {
    val grid = """
        #########
        #S| | | #
        #-#-#-#-#
        # | | | #
        #-#-#-#-#
        # | | | #
        #-#-#-#-#
        # | | |  
        ####### V
    """.trimIndent().split("\n")

    val newGrid = buildList {
        for(rowI in grid.indices) {
            val row = grid[rowI]
            val newRow = buildList {
                for(colI in row.indices) {
                    val entity = grid[rowI][colI]
                    add(when (entity){
                        '#' -> Entity.WALL
                        'S' -> Entity.START
                        '|' -> Entity.DOOR
                        '-' -> Entity.DOOR
                        'V' -> Entity.VAULT
                        else -> Entity.SPACE
                    })
                }
            }
            add(newRow)
        }
    }
    return newGrid
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day17_test").first()
    check(part1(testInput) == "DRURDRUDDLLDLUURRDULRLDUUDDDRR")

    val input = readInput("Day17").first()
    check(part1(input) == "DRRDRLDURD")
}
 