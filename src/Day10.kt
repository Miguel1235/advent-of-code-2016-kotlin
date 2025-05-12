private fun part(input: List<String>, pairComparing: Pair<Int, Int> = Pair(0,0), isPart1: Boolean = true): Int {
    val bots = mutableMapOf<Int, MutableList<Int>>()
    val outputs = mutableMapOf<Int, MutableList<Int>>()

    val valueRegex = Regex("""value (\d+) goes to bot (\d+)""")
    val instructionRegex = Regex("""bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""")

    val remainingInstructions = input.toMutableList()

    var currentInstruction = 0

    while (remainingInstructions.isNotEmpty()) {
        if(isPart1) {
            bots.entries.find { it.value.sorted() == pairComparing.toList().sorted()}?.let {
                return it.key
            }
        }

        val instruction = remainingInstructions.getOrElse(currentInstruction) {
            currentInstruction = 0
            remainingInstructions.first()
        }

        valueRegex.find(instruction)?.groupValues?.let {
            val (value, bot) = it.drop(1)
            bots.getOrPut(bot.toInt()) { mutableListOf() }.add(value.toInt())
            remainingInstructions.removeAt(currentInstruction)
            currentInstruction = 0
            continue
        }

        val (botNumber, lowType, lowValue, highType, highValue) = instructionRegex.find(instruction)!!.groupValues.drop(1)

        val donatorBot = bots[botNumber.toInt()]
        if (donatorBot == null || donatorBot.size != 2) {
            currentInstruction++
            continue
        }

        val low = donatorBot.min()
        val high = donatorBot.max()

        val low2Add = if (lowType == "bot") bots else outputs
        low2Add.getOrPut(lowValue.toInt()) { mutableListOf() }.add(low)

        val high2Add = if (highType == "bot") bots else outputs
        high2Add.getOrPut(highValue.toInt()) { mutableListOf() }.add(high)

        donatorBot.clear()
        remainingInstructions.removeAt(currentInstruction)
    }
    return outputs[0]!!.first() * outputs[1]!!.first() * outputs[2]!!.first()
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part(testInput, Pair(2,5)) == 2)
    check(part(testInput, isPart1 = false) == 30)

    val input = readInput("Day10")
    check(part(input, Pair(17,61)) == 161)
    check(part(input, isPart1 = false) == 133163)
}
 