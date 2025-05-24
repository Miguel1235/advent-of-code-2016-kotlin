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

private data class State(val row: Int, val col: Int, val input: String)

private fun part(input: String, isPart2: Boolean = false): String {
    val grid = parseInput()
    val start = findEntity(grid,Entity.START)
    val endPoint = findEntity(grid, Entity.VAULT)

    val queue = ArrayDeque<State>()
    val startState = State(start.first, start.second, input)
    queue.add(startState)

    var maxLength = -1
    var minLength = ""
    val visited = mutableSetOf<String>()
    while(queue.isNotEmpty()) {
        val (row, col, input) = queue.removeFirst()
        val stateKey = "$row,$col,$input"
        if(stateKey in visited) continue
        visited.add(stateKey)

        if(row == endPoint.first - 1 && col == endPoint.second - 1) {
            val result = input.toList().filter { it.isUpperCase() }.joinToString("")
            if(result.length > maxLength) maxLength = result.length
            if(result.length < minLength.length || minLength == "") minLength = result
            continue
        }
        for (direction in DeltaDirection.entries) {
            val newRow = row + direction.rowDelta
            val newCol = col + direction.colDelta

            val entity = grid.getOrNull(newRow)?.getOrNull(newCol)
            if(entity == null) continue
            if(entity != Entity.DOOR) continue

            val (up, down, left, right) = input.md5().take(4).toList().map { listOf('b', 'c','d', 'e', 'f').contains(it) }
            if(up && direction.name == "UP") queue.add(State(newRow-1, newCol, "${input}U"))
            if(down && direction.name == "DOWN") queue.add(State(newRow+1, newCol, "${input}D"))
            if(left && direction.name == "LEFT") queue.add(State(newRow, newCol-1, "${input}L"))
            if(right && direction.name == "RIGHT") queue.add(State(newRow, newCol+1, "${input}R"))
        }
    }
    return if(isPart2) maxLength.toString() else minLength
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

fun main() {
    val testInput = readInput("Day17_test").first()
    check(part(testInput) == "DRURDRUDDLLDLUURRDULRLDUUDDDRR")
    check(part(testInput, true).toInt() == 830)
    val input = readInput("Day17").first()
    check(part(input) == "DRRDRLDURD")
    check(part(input, true).toInt() == 618)
}
 