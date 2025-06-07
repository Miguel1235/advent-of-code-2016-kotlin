data class Node(val row: Int, val col: Int, val size: Int, val used: Int, val aval: Int)

private fun part1(nodes: List<Node>): Int {
    val pairs = buildList {
        for (i in 0 until nodes.size) {
            for (j in i + 1 until nodes.size) {
                val nodeA = nodes[i]
                val nodeB = nodes[j]
                if (isValidPair(nodeA, nodeB) || isValidPair(nodeB, nodeA)) add(Pair(nodeA, nodeB))
            }
        }
    }
    return pairs.size
}

private fun isValidPair(nodeA: Node, nodeB: Node): Boolean {
    if (nodeA.used == 0) return false
    if (nodeA.used > nodeB.aval) return false
    return true
}

private fun parseInput(input: List<String>): List<Node> {
    val dfRegex = Regex("""node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+(\d+)T\s+(\d+%)""")
    return buildList {
        for (line in input) {
            val (r, c, size, used, aval) = dfRegex.find(line)!!
                .groupValues.drop(1)
                .dropLast(1)
                .map { it.toInt() }
            add(Node(r,c, size, used, aval))
        }
    }
}

private fun part2(nodes: List<Node>): Int {
    val targetNode = nodes.filter { it.col == 0 }.sortedBy { it.row }.reversed().first()
    println(targetNode)
    return 0
}

fun main() {
    val testInput = parseInput(readInput("Day22_test").drop(2))
    part2(testInput)

    val input = parseInput(readInput("Day22").drop(2))
    check(part1(input) == 901)
}
 