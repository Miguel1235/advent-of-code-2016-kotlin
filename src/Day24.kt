import java.util.LinkedList
import java.util.Queue

private fun part1(input: List<List<Layout>>): Int {
    val locations = findRelevantLocations(input)
    val startPoint = findStartPoint(input)
    return 0
}

fun bfs(maze: List<List<Layout>>, start: Point): Map<Point, Int> {
    val queue: Queue<Point> = LinkedList()
    val visited = mutableSetOf<Point>()
    val distance = mutableMapOf<Point, Int>()

    queue.add(start)
    visited.add(start)
    distance[start] = 0

    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        val currentDist = distance[current]!!

        for ((dx, dy) in directions) {
            val next = Point(current.r + dx, current.c + dy)

            if (next.c in maze.indices &&
                next.r in maze[0].indices &&
                maze[next.c][next.r] != Layout.WALL &&
                next !in visited
            ) {
                visited.add(next)
                distance[next] = currentDist + 1
                queue.add(next)
            }
        }
    }

    return distance
}

private fun part2(input: List<String>): Int {
    return 0
}

enum class Layout {
    WALL,
    SPACE,
    RELEVANT_LOCATION,
    START_POINT
}

data class Point(val r: Int, val c: Int)

private fun findRelevantLocations(maze: List<List<Layout>>): List<Point> {
    return buildList {
        for(r in 0 until maze.size) {
            for(c in 0 until maze[r].size) {
                if(maze[r][c].name === Layout.RELEVANT_LOCATION.name) {
                    add(Point(r,c))
                }
            }
        }
    }
}

private fun findStartPoint(maze: List<List<Layout>>): Point {
        for(r in 0 until maze.size) {
            for(c in 0 until maze[r].size) {
                if(maze[r][c].name === Layout.START_POINT.name) {
                    return Point(r,c)
                }
            }
        }
    return Point(0, 0)
}


private fun parseInput(input: List<String>): List<List<Layout>> {
    return buildList {
        for(row in input) {
            add(
                row.toList().map {
                    when(it) {
                        '#' -> Layout.WALL
                        '.' -> Layout.SPACE
                        '0' -> Layout.START_POINT
                        else -> Layout.RELEVANT_LOCATION
                    }
                }
            )
        }
    }
}

fun main() {
    val testInput = parseInput(readInput("Day24_test"))
    part1(testInput)
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
    val input = parseInput(readInput("Day24"))
    part1(input)
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 