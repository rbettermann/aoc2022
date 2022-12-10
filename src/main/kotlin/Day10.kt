class Day10 {

    companion object {
        val relevantCycles = listOf(20, 60, 100, 140, 180, 220)
    }

    private fun parseInput(input: String) = input.split("\n").filter { it.isNotEmpty() }

    fun part1(input: String): Int {
        val parsed = parseInput(input)
        return executeProgram(parsed)
    }

    private fun executeProgram(ops: List<String>): Int {
        var register = 1
        var cycle = 0

        val ret = mutableListOf<Int?>()

        ops.forEach { op ->
            if (op == "noop") {
                // noop
                cycle += 1
                ret.add(checkSignal(cycle, register))
            } else {
                val x = op.split(" ")[1].toInt()

                cycle += 1
                ret.add(checkSignal(cycle, register))



                cycle += 1
                ret.add(checkSignal(cycle, register))
                register += x
            }
        }

        val nonNull = ret.filterNotNull()
        assert(nonNull.size == relevantCycles.size)

        return nonNull.sum()
    }

    private fun checkSignal(cycles: Int, register: Int): Int? {
        return if (relevantCycles.contains(cycles)) {
            cycles * register
        } else {
            null
        }
    }

    fun part2(input: String): String {
        val parsed = parseInput(input)
        return drawScreen(parsed)
    }

    private fun drawScreen(ops: List<String>): String {
        var register = 1
        var cycle = 0

        var out = ""

        ops.forEach { op ->
            if (op == "noop") {
                out += drawPixel(cycle, register)
                // noop
                cycle += 1


            } else {
                val x = op.split(" ")[1].toInt()

                out += drawPixel(cycle, register)
                cycle += 1

                out += drawPixel(cycle, register)
                cycle += 1
                register += x

            }
        }
        return out
    }

    private fun drawPixel(cycle: Int, register: Int): String {
        var out = ""
        if (cycle % 40 == 0) {
            out += "\n"
        }

        val spriteLeft = register - 1
        val spriteMid = register
        val spriteRight = register + 1

        if (listOf(spriteLeft, spriteMid, spriteRight).contains(cycle % 40)) {
            out += "#"
        } else {
            out += "."
        }
        return out
    }
}