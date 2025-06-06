data class Node(val name: String, val size: Int, val used: Int, val aval: Int)

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
    if (nodeA.name == nodeB.name) return false
    if (nodeA.used > nodeB.aval) return false
    return true
}

private fun parseInput(input: List<String>): List<Node> {
    val dfRegex = Regex("""node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+(\d+)T\s+(\d+%)""")
    return buildList {
        for (line in input) {
            val (r, c, size, used, aval) = dfRegex.find(line)!!
                .groupValues.drop(1)
            add(Node("$r-$c", size.toInt(), used.toInt(), aval.toInt()))
        }
    }
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {

    val input = parseInput(readInput("Day22").drop(2))
    check(part1(input) == 901)
}
 