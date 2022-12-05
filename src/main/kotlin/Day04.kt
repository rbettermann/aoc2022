class Day04 {
    private fun calculateRange(input: String): IntRange {
        val s = input.split("-")
        return s[0].toInt() .. s[1].toInt()
    }

    private fun parseInput(input: String) = input.split("\n").map { it.split(",") }
        .map { listOf(calculateRange(it[0]), calculateRange(it[1])) }

    private fun isFullyOverlapping(r1: IntRange, r2: IntRange) =
        r1.contains(r2.first) && r1.contains(r2.last) || r2.contains(r1.first) && r2.contains(r1.last)

    private fun isPartiallyOverlapping(r1: IntRange, r2: IntRange) =
        r1.intersect(r2).isNotEmpty() || r2.intersect(r1).isNotEmpty()

    fun part1(input: String) = parseInput(input).count { isFullyOverlapping(it[0], it[1]) }
    fun part2(input: String): Int = parseInput(input).count { isPartiallyOverlapping(it[0], it[1]) }
}