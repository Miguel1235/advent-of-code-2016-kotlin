private fun part1(input: List<AssembunnyInstruction>, isPart2: Boolean = false): Int {
    var instructionCounter = 0
    val registers = mutableMapOf<String, Int>(
        "a" to 0,
        "b" to 0,
        "c" to if(isPart2) 1 else 0,
        "d" to 0,
    )

    while (instructionCounter < input.size) {
        val (name, op1, op2) = input[instructionCounter]
        when (name) {
            InsName.COPY -> {
                val number2Copy = registers.getOrElse(op1) { op1.toInt() }
                registers.put(op2.toString(), number2Copy)
            }
            InsName.JNZ -> {
                val op1Value = registers.getOrElse(op1) { op1.toInt() }

                if (op1Value != 0) {
                    instructionCounter = instructionCounter + op2!!.toInt()
                    continue
                }
            }
            InsName.INC -> {
                var current = registers.getOrPut(op1) { 0 }
                registers.put(op1, ++current)
            }
            InsName.DEC -> {
                var current = registers.getOrPut(op1) { 0 }
                registers.put(op1, --current)
            }
            InsName.TGL -> TODO()
        }
        instructionCounter++
    }
    return registers.getOrElse("a") { 0 }
}

enum class InsName {
    COPY,
    JNZ,
    INC,
    DEC,
    TGL
}

data class AssembunnyInstruction(val name: InsName, val op1: String, val op2: String? = null)

private fun parseInput(input: List<String>): List<AssembunnyInstruction> {
    val twoOperandsRegex = Regex("""(cpy|jnz) (.+) (.+)""")
    val singleOperandRegex = Regex("""(inc|dec) (\w+)""")

    val instructions = buildList {
        for (line in input) {
            twoOperandsRegex.find(line)?.groupValues?.let {
                val (instruction, op1, op2) = it.drop(1)
                add(AssembunnyInstruction(if (instruction == "jnz") InsName.JNZ else InsName.COPY, op1, op2))
            }
            singleOperandRegex.find(line)?.groupValues?.let {
                val (instruction, op1) = it.drop(1)
                add(AssembunnyInstruction(if (instruction == "inc") InsName.INC else InsName.DEC, op1))
            }
        }
    }
    return instructions
}

fun main() {
    val testInput = parseInput(readInput("Day12_test"))
    check(part1(testInput) == 42)
    check(part1(testInput, true) == 42)

    val input = parseInput(readInput("Day12"))
    check(part1(input) == 318007)
    check(part1(input, true) == 9227661)
}
 