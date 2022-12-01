class Day01 {
    private fun parseInput(input: String): List<List<Int>> {
        return input.split("\n\n").map { it.split("\n") }.map { it.map { it.toInt() } }
    }

    fun part1(input: String): Int {
        val elfs = parseInput(input)
        return elfs.map { it.sum() }.max()
    }

    fun part2(input: String): Int {
        val elfs = parseInput(input)
        return elfs.map { it.sum() }.sortedDescending().take(3).sum()
    }
}