private fun part1(input: List<String>, text: String = "abcde"): String {
    val swapRegex = Regex("""swap position (\d+) with position (\d+)""")
    val swapLetter = Regex("""swap letter (\w) with letter (\w)""")
    val reverseRegex = Regex("""reverse positions (\d+) through (\d+)""")
    val rotateRegex = Regex("""rotate (left|right) (\d+) step""")
    val positionRegex = Regex("""move position (\d+) to position (\d+)""")
    val rotateBasedRegex = Regex("""rotate based on position of letter (\w)""")
    var scrambledText = text

    for (instruction in input) {
        val swap = swapRegex.find(instruction)?.groupValues
        if (swap != null) {
            val (pos1, pos2) = swap.drop(1).map { it.toInt() }
            val temp = scrambledText[pos1]
            scrambledText = scrambledText.replaceRange(pos1, pos1 + 1, scrambledText[pos2].toString())
            scrambledText = scrambledText.replaceRange(pos2, pos2 + 1, temp.toString())
        }

        val swapLetter = swapLetter.find(instruction)?.groupValues
        if (swapLetter != null) {
            val (letter1, letter2) = swapLetter.drop(1)
            scrambledText = buildString {
                for (char in scrambledText) {
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

        val reverse = reverseRegex.find(instruction)?.groupValues
        if (reverse != null) {
            val (pos1, pos2) = reverse.drop(1).map { it.toInt() }
            scrambledText =
                scrambledText.replaceRange(pos1, pos2 + 1, scrambledText.substring(pos1, pos2 + 1).reversed())
        }

        val rotate = rotateRegex.find(instruction)?.groupValues
        if (rotate != null) {
            val (direction, steps) = rotate.drop(1)
            val stepsInt = steps.toInt()
            scrambledText =
                if (direction == "left") scrambledText.drop(stepsInt) + scrambledText.take(stepsInt) else scrambledText.takeLast(
                    stepsInt
                ) + scrambledText.dropLast(stepsInt)
        }

        val position = positionRegex.find(instruction)?.groupValues
        if (position != null) {
            val (pos1, pos2) = position.drop(1).map { it.toInt() }

            val temp = scrambledText[pos1]
            val removed = scrambledText.removeRange(pos1, pos1 + 1).toMutableList()
            removed.add(pos2, temp)
            scrambledText = removed.joinToString("")
        }

        val rotateBased = rotateBasedRegex.find(instruction)?.groupValues
        if (rotateBased != null) {
            val letter = rotateBased.drop(1).first()
            var index = scrambledText.indexOf(letter)

            val oneRotation = scrambledText.takeLast(1) + scrambledText.dropLast(1)

            if (index >= 4) index++
            val totalR = oneRotation.takeLast(index) + oneRotation.dropLast(index)
            scrambledText = totalR
        }
    }
    return scrambledText
}

fun main() {
    val testInput = readInput("Day21_test")
    check(part1(testInput) == "decab")

    val input = readInput("Day21")
    check(part1(input, "abcdefgh") == "dbfgaehc")
}
 