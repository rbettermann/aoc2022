class Day05 {
    companion object {
        const val EMPTY = "[$]"
    }

    class Instruction(
        val number: Int,
        val from: Int,
        val to: Int
    )

    private fun parseStacks(input: String): List<ArrayDeque<String>> {
        val stack = input.split("\n\n")[0].split("\n")
        val stackAlone = stack.subList(0, stack.lastIndex).joinToString("\n")
            .replace("    [", "$EMPTY [")
            .replace("]    ", "] $EMPTY")
            .replace("    ", " $EMPTY")

        val numberOfStacks = stackAlone.split("\n")[0].count { it == '[' }

        val stacks = mutableListOf<ArrayDeque<String>>()
        (0 until numberOfStacks).forEach { stacks.add(ArrayDeque()) }

        stackAlone.split("\n").map { it.split(" ") }
            .forEach {
                it.forEachIndexed { index, value ->
                    if (value != EMPTY) {
                        stacks[index].addLast(value.replace("[", "").replace("]", ""))
                    }
                }
            }
        return stacks
    }

    private fun parseInstructions(input: String): List<Instruction> {
        return input.split("\n\n")[1]
            .split("\n").map {
                it.replace("move", "")
                    .replace("from", ",")
                    .replace("to", ",").split(",")
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toInt() }
            }.filter { it.isNotEmpty() }
            .map {
                Instruction(it[0], it[1] - 1, it[2] - 1)
            }
    }

    fun part1(input: String): String {
        val stacks = parseStacks(input)
        val instructions = parseInstructions(input)
        instructions.forEach { op ->
            (0 until op.number).forEach { _ ->
                stacks[op.to].addFirst(stacks[op.from].removeFirst())
            }
        }
        return stacks.joinToString("") { it.first() }
    }

    fun part2(input: String): String {
        val stacks = parseStacks(input)
        val instructions = parseInstructions(input)
        instructions.forEach { op ->
            (0 until op.number).map {
                stacks[op.from].removeFirst()
            }.reversed().forEach {
                stacks[op.to].addFirst(it)
            }
        }
        return stacks.joinToString("") { it.first() }
    }
}