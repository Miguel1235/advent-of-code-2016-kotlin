private fun part1(input: List<AssembunnyInstruction>, isPart2: Boolean = false): Int {
    var instructionCounter = 0
    val registers = mutableMapOf(
        "a" to if(isPart2) 12 else 7,
        "b" to 0,
        "c" to 0,
        "d" to 0,
    )

    val instructions = input.toMutableList()

    while (instructionCounter < instructions.size) {
        println(registers)
//        println("***")
//        instructions.forEach { println(it) }
//        println("***")

        val (name, op1, op2) = instructions[instructionCounter]
        println("instruction: $name $op1 $op2")
        when (name) {
            InsName.COPY -> {
                val number2Copy = registers.getOrElse(op1) { op1.toInt() }
                registers.put(op2.toString(), number2Copy)
            }
            InsName.JNZ -> {
                val op1Value = registers.getOrElse(op1) { op1.toInt() }
                val op2Value = op2?.toIntOrNull() ?: registers[op2.toString()]!!
                if (op1Value != 0) {
                    instructionCounter = instructionCounter + op2Value
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
            InsName.TGL -> {
                val offset = registers.getOrElse(op1) { op1.toInt() }
                val ins2Change = instructions.getOrNull(instructionCounter + offset)

                if(ins2Change != null) {
                    when(ins2Change.name) {
                        InsName.COPY -> instructions[instructionCounter + offset] = AssembunnyInstruction(InsName.JNZ,ins2Change.op1, ins2Change.op2)
                        InsName.JNZ -> instructions[instructionCounter + offset] = AssembunnyInstruction(InsName.COPY,ins2Change.op1, ins2Change.op2)
                        InsName.INC -> instructions[instructionCounter + offset] = AssembunnyInstruction(InsName.DEC,ins2Change.op1, ins2Change.op2)
                        InsName.DEC -> instructions[instructionCounter + offset] = AssembunnyInstruction(InsName.INC,ins2Change.op1, ins2Change.op2)
                        InsName.TGL -> instructions[instructionCounter + offset] = AssembunnyInstruction(InsName.INC,ins2Change.op1)
                    }
                }
            }
        }
        instructionCounter++
    }
    return registers.getOrElse("a") { 0 }
}



private fun parseInput(input: List<String>): List<AssembunnyInstruction> {
    val twoOperandsRegex = Regex("""(cpy|jnz) (.+) (.+)""")
    val singleOperandRegex = Regex("""(inc|dec|tgl) (\w+)""")

    val instructions = buildList {
        for (line in input) {
            twoOperandsRegex.find(line)?.groupValues?.let {
                val (instruction, op1, op2) = it.drop(1)
                add(AssembunnyInstruction(if (instruction == "jnz") InsName.JNZ else InsName.COPY, op1, op2))
            }
            singleOperandRegex.find(line)?.groupValues?.let {
                val (instruction, op1) = it.drop(1)

                val ins = when(instruction) {
                    "inc" -> InsName.INC
                    "dec" -> InsName.DEC
                    else -> InsName.TGL
                }

                add(AssembunnyInstruction(ins, op1))
            }
        }
    }
    return instructions
}

fun main() {
//    val testInput = parseInput(readInput("Day23_test"))
//    check(part1(testInput) == 3)
//    check(part1(testInput, true) == 42)

    val input = parseInput(readInput("Day23"))
//    check(part1(input) == 12703)
    part1(input, true).println()
//    check(part1(input, true) == 9227661)
}
 