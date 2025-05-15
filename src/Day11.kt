import kotlin.collections.addAll

data class Chip(val name: String, val floor: Int)
data class Generator(val name: String, val floor: Int)

private fun obtainChips(input: List<String>): List<Chip> {
    val chipsRegex = Regex("""(\w+)-compatible""")
    return buildList {
        input.mapIndexed { floor, line ->
            addAll(
                chipsRegex
                    .findAll(line)
                    .map { it.groupValues.last() }
                    .map { Chip(it, floor) }
            )
        }
    }
}

private fun obtainGenerators(input: List<String>): List<Generator> {
    val generatorRegex = Regex("""(\w+) generator""")
    return buildList {
        input.mapIndexed { floor, line ->
            addAll(
                generatorRegex
                    .findAll(line)
                    .map { it.groupValues.last() }
                    .map { Generator(it, floor) }
            )
        }
    }
}

private fun part1(input: List<String>): Int {
    val generators = obtainGenerators(input)
    val chips = obtainChips(input)
    var currentFloor = 0
    val targetFloor = 3


    for(chip in chips) {
        println(chip)
        val genF = generators
            .filter { it.name != chip.name }
            .map { it.floor }


        if(chip.floor in genF) {
            println("We need to move the chip to not burn it")
        } else {
            println("we are fine bru")
        }
    }

    return 0
}

//private fun part2(input: List<String>): Int {
//    return 0
//}


fun main() {
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
//    val input = readInput("Day11")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 