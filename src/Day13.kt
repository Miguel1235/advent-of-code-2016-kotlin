private fun part1(maze: List<List<Char>>, end: Pair<Int, Int> = Pair(4,7)): Int {
    val start = Pair(1, 1) // row, col

    val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

    val queue: ArrayDeque<Triple<Int, Int, Int>> = ArrayDeque()
    val visited = Array(maze.size) { BooleanArray(maze[0].size) { false } }
    queue.add(Triple(start.first, start.second, 0))

    while (queue.isNotEmpty()) {

        val (row, col, steps) = queue.removeFirst()

        if (row == end.first && col == end.second) {
            return steps
        }

        for ((deltaR, deltaC) in directions) {
            val newRow = row + deltaR
            val newCol = col + deltaC

            if (maze.getOrNull(newRow)?.getOrElse(newCol) { '@' } != '.') continue
            if(visited[newRow][newCol]) continue


            queue.add(Triple(newRow, newCol, steps + 1))
            visited[newRow][newCol] = true
        }
    }
    return -1
}

private fun generateMaze(favoriteNumber: Int, dimensions: Pair<Int, Int> = Pair(6,9)): List<List<Char>> {
    return buildList {
        for (r in 0..dimensions.first) {
            val row = buildList {
                for (c in 0..dimensions.second) {
                    val result = (c * c + 3 * c + 2 * r * c + r + r * r) + favoriteNumber
                    val binaryRep = result.toString(2).padStart(12, '0')
                    val isOdd = binaryRep.toList().count { it == '1' } % 2

                    add(if (isOdd == 1) '#' else '.')
                }
            }
            add(row)
        }
    }
}

private fun prettyPrint(maze: List<List<Char>>) {
    for (r in maze.indices) {
        for (c in maze[r].indices) {
            print(maze[r][c])
        }
        println()
    }
    println("+******+")
}

fun main() {
    val testInput = generateMaze(readInput("Day13_test").first().toInt(), Pair(40,40))
    check(part1(testInput) == 11)

    val input = generateMaze(readInput("Day13").first().toInt(), Pair(50,50))
    check(part1(input, Pair(39,31)) == 82)
}
 