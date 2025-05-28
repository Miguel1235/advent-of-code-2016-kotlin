private fun part1(input: List<String>): Int {
    val swapRegex = Regex("""swap position (\d+) with position (\d+)""")
    val swapLetter = Regex("""swap letter (\w) with letter (\w)""")
    val reverseRegex = Regex("""reverse positions (\d+) through (\d+)""")
    val rotateRegex = Regex("""rotate (left|right) (\d+) step""")
    val positionRegex = Regex("""move position (\d+) to position (\d+)""")
    val rotateBasedRegex = Regex("""rotate based on position of letter (\w)""")

    var scrambledText = "abcde"
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
            val newText = scrambledText.drop(stepsInt) + scrambledText.take(stepsInt)
            scrambledText = if (direction == "left") {
                newText
            } else {
                newText.reversed()
            }
        }

        val position = positionRegex.find(instruction)?.groupValues
        if (position != null) {
            val (pos1, pos2) = position.drop(1).map { it.toInt() }
            val temp = scrambledText[pos1]

            val r = buildString {
                for (i in scrambledText.indices) {
                    if (i == pos1) {
                        append("")
                        continue
                    }
                    if (i == pos2) {
                        if (pos2 == 0) {
                            append(temp)
                            append(scrambledText[i])

                        } else {
                            append(scrambledText[i])
                            append(temp)
                        }

                        continue
                    }
                    append(scrambledText[i])
                }
            }
            scrambledText = r
        }

        val rotateBased = rotateBasedRegex.find(instruction)?.groupValues
        if (rotateBased != null) {
            val (letter) = rotateBased.drop(1)
            var index = scrambledText.indexOf(letter.first())
//            println("text: $scrambledText -- index: $index -- letter: $letter")

            index = if (index >= 4) {
                index + 1 + 1
            } else {
                index + 1 + 1
            }

            var tt = scrambledText.drop(index) + scrambledText.take(index)
            tt = tt.reversed()

            scrambledText = tt
        }
    }
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

//    val input = readInput("Day21")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 