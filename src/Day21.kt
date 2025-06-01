import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.drop
import kotlin.text.drop
import kotlin.text.iterator

private fun swapPositions(ins: List<String>, text: String): String {
    val (pos1, pos2) = ins.drop(1).map { it.toInt() }
    var scrambledText = text

    val temp = scrambledText[pos1]
    scrambledText = scrambledText.replaceRange(pos1, pos1 + 1, scrambledText[pos2].toString())
    scrambledText = scrambledText.replaceRange(pos2, pos2 + 1, temp.toString())
    return scrambledText
}

private fun swapLetters(ins: List<String>, text: String): String {
    val (letter1, letter2) = ins.drop(1)
    return buildString {
        for (char in text) {
            if (char == letter1.first()) {
                append(letter2.first())
                continue
            }
            if (char == letter2.first()) {
                append(letter1.first())
                continue
            }
            append(char)
        }
    }
}

private val reverse = { ins: List<String>, text: String ->
    val (pos1, pos2) = ins.drop(1).map { it.toInt() }
    text.replaceRange(pos1, pos2 + 1, text.substring(pos1, pos2 + 1).reversed())
}

private val rotate = { ins: List<String>, text: String ->
    val (direction, steps) = ins.drop(1)
    val stepsInt = steps.toInt()

    if (direction == "left") text.drop(stepsInt) + text.take(stepsInt)
    else text.takeLast(stepsInt) + text.dropLast(stepsInt)
}

private val movePosition = { ins: List<String>, text: String ->
    val (pos1, pos2) = ins.drop(1).map { it.toInt() }
    val temp = text[pos1]
    val removed = text.removeRange(pos1, pos1 + 1).toMutableList()
    removed.add(pos2, temp)
    removed.joinToString("")
}

private val rotateBased = { ins: List<String>, text: String ->
    val letter = ins.drop(1).first()
    var index = text.indexOf(letter)

    val oneRotation = text.takeLast(1) + text.dropLast(1)

    if (index >= 4) index++
    oneRotation.takeLast(index) + oneRotation.dropLast(index)
}

enum class InstructionType {
    SWAP_POSITION,
    SWAP_LETTER,
    REVERSE_POSITION,
    ROTATE,
    MOVE_POSITION,
    ROTATE_BASED
}

private fun parseInput(input: List<String>): List<Pair<InstructionType, List<String>>> {
    val swapPositionRegex = Regex("""swap position (\d+) with position (\d+)""")
    val swapLetterRegex = Regex("""swap letter (\w) with letter (\w)""")
    val reverseRegex = Regex("""reverse positions (\d+) through (\d+)""")
    val rotateRegex = Regex("""rotate (left|right) (\d+) step""")
    val movePositionRegex = Regex("""move position (\d+) to position (\d+)""")
    val rotateBasedRegex = Regex("""rotate based on position of letter (\w)""")

    return buildList {
        for (instruction in input) {
            val swapPosition = swapPositionRegex.find(instruction)?.groupValues
            if(swapPosition != null) add(Pair(InstructionType.SWAP_POSITION, swapPosition))

            val swapLetter = swapLetterRegex.find(instruction)?.groupValues
            if(swapLetter != null) add(Pair(InstructionType.SWAP_LETTER, swapLetter))

            val reverse = reverseRegex.find(instruction)?.groupValues
            if (reverse != null) add(Pair(InstructionType.REVERSE_POSITION, reverse))

            val rotate = rotateRegex.find(instruction)?.groupValues
            if (rotate != null) add(Pair(InstructionType.ROTATE, rotate))

            val movePosition = movePositionRegex.find(instruction)?.groupValues
            if (movePosition != null) add(Pair(InstructionType.MOVE_POSITION, movePosition))

            val rotateBased = rotateBasedRegex.find(instruction)?.groupValues
            if (rotateBased != null) add(Pair(InstructionType.ROTATE_BASED, rotateBased))
        }
    }
}

private fun part1(instructions:  List<Pair<InstructionType, List<String>>>, text: String = "abcde"): String {
    var scrambledText = text

    for ((type, args) in instructions) {
        scrambledText = when(type) {
            InstructionType.SWAP_POSITION -> swapPositions(args, scrambledText)
            InstructionType.SWAP_LETTER -> swapLetters(args, scrambledText)
            InstructionType.REVERSE_POSITION -> reverse(args, scrambledText)
            InstructionType.ROTATE -> rotate(args, scrambledText)
            InstructionType.MOVE_POSITION -> movePosition(args, scrambledText)
            InstructionType.ROTATE_BASED -> rotateBased(args, scrambledText)
        }
    }
    return scrambledText
}

fun main() {
    val testInput = parseInput(readInput("Day21_test"))
    check(part1(testInput) == "decab")

    val input = parseInput(readInput("Day21"))
    check(part1(input, "abcdefgh") == "dbfgaehc")
}
 