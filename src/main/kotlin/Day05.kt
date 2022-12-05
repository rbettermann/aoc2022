class Day05 {
    private fun parseInstructions(input: String): List<List<Int>> {
        val instructions = input.split("\n\n")[1]
            .split("\n").map {
                it.replace("move", "")
                    .replace("from", ",")
                    .replace("to", ",").split(",")
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toInt() }
            }.filter { it.isNotEmpty() }
        return instructions
    }

    private fun parseStacks(input: String): List<ArrayDeque<String>> {
        val stack = input.split("\n\n")[0].split("\n")
        val stackAlone = stack.subList(0, stack.lastIndex).joinToString("\n")
            .replace("    [", "[$] [")
            .replace("]    ", "] [$]")
            .replace("    ", " [$]")

        val numberOfStacks = stackAlone.split("\n")[0].count { it == '[' }

        val stacks = mutableListOf<ArrayDeque<String>>()
        (0 until numberOfStacks).forEach { _ -> stacks.add(ArrayDeque()) }

        stackAlone.split("\n").map { it.split(" ") }
            .forEach {
                it.forEachIndexed { index, value ->
                    if (value == "[$]") {
                        //do nothing
                    } else {
                        stacks[index].addLast(value.replace("[", "").replace("]", ""))
                    }
                }
            }
        return stacks
    }

    fun part1(input: String): String {
        val stacks = parseStacks(input)
        val instructions = parseInstructions(input)

        instructions.forEach {
            val number = it[0]
            val from = it[1] - 1
            val to = it[2] - 1

            (0 until number).forEach {
                stacks[to].addFirst(stacks[from].removeFirst())
            }
        }

        return stacks.map { it.firstOrNull() }.filterNotNull().joinToString("")
    }

    fun part2(input: String): String {
        val stacks = parseStacks(input)
        val instructions = parseInstructions(input)

        instructions.forEach {
            val number = it[0]
            val from = it[1] - 1
            val to = it[2] - 1

            (0 until number).map {
                stacks[from].removeFirst()
            }.reversed().forEach {
                stacks[to].addFirst(it)
            }
        }
        return stacks.joinToString("") { it.first() }
    }
}