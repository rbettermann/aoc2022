class Day05 {

    fun part1(input: String): String {
        val stack = input.split("\n\n")[0].split("\n")
        val stackAlone = stack.subList(0, stack.lastIndex).joinToString("\n")
            .replace("    [", "[$] [")
            .replace("]    ", "] [$]")
            .replace("    ", " [$]")

        val stacks = mutableListOf<ArrayDeque<String>>()
        (0 until 10).forEach { _ -> stacks.add(ArrayDeque()) }

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

        val instructions = input.split("\n\n")[1]
            .split("\n").map {
                it.replace("move", "")
                    .replace("from", ",")
                    .replace("to", ",").split(",")
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toInt()}
            }.filter { it.isNotEmpty() }

        println("before $stacks")

        instructions.forEach {
            val number = it[0]
            val from = it[1] - 1
            val to = it[2] - 1

            //println("move $number from $from to $to")

            (0 until number).forEach {
                //println("X $it $number")
                stacks[to].addFirst(stacks[from].removeFirst())
            }

            //println(stacks)
        }

        return stacks.map { it.firstOrNull() }.filterNotNull().joinToString("")
    }

    fun part2(input: String): String {
        val stack = input.split("\n\n")[0].split("\n")
        val stackAlone = stack.subList(0, stack.lastIndex).joinToString("\n")
            .replace("    [", "[$] [")
            .replace("]    ", "] [$]")
            .replace("    ", " [$]")

        val stacks = mutableListOf<ArrayDeque<String>>()
        (0 until 10).forEach { _ -> stacks.add(ArrayDeque()) }

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

        val instructions = input.split("\n\n")[1]
            .split("\n").map {
                it.replace("move", "")
                    .replace("from", ",")
                    .replace("to", ",").split(",")
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toInt()}
            }.filter { it.isNotEmpty() }

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

        return stacks.map { it.firstOrNull() }.filterNotNull().joinToString("")
    }
}